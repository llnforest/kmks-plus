package com.kmks.w2.service.impl.supervise;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.bo.*;
import com.kmks.jianguan.domain.vo.*;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.service.ISuperviseService;
import com.kmks.w2.service.IW2LineconfigService;
import com.kmks.w2.service.impl.ImageServiceImpl;
import com.kmks.w2.utils.RedisUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: 单机模式
 * @date 2024/9/3 13:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StandAloneServiceImpl implements ISuperviseService {

    private final ISysConfigService configService;

    private final W2RecordsMapper recordsMapper;

    private final W2QueuingMapper queuingMapper;

    private final W2KcxxMapper kcxxMapper;

    private final ImageServiceImpl imageService;

    private final CommonHandlerService commonHandlerService;

    /**
     * 同步时间
     */
    @Override
    public String syncTime() {
        log.info("无需同步时间");
        return DateUtil.now();
    }

    /**
     * 报到
     *
     * @param checkInBo checkInBo
     * @return {@link Boolean}
     */
    @Override
    public Boolean checkIn(CheckInBo checkInBo) {
        // 判断此人是否在今日考试记录中报到
        List<W2RecordsVo> w2RecordsVos = recordsMapper.selectVoList(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, checkInBo.getZjhm())
                        .eq(W2Records::getYkrq, DateUtil.today())
        );
        if (w2RecordsVos == null || w2RecordsVos.isEmpty()) {
            throw new FailException("报到失败，今日无该约考考生");
        }

        //约考信息录入考试信息
        W2Records w2Records = new W2Records();
        w2Records.setZjzp(imageService.saveImage(checkInBo.getZjzp()));
        w2Records.setJbzp(imageService.saveImage(checkInBo.getJbzp()));
        if (recordsMapper.update(w2Records, Wrappers.lambdaUpdate(W2Records.class)
                .eq(W2Records::getZjhm, w2RecordsVos.get(0).getZjhm())
                .eq(W2Records::getYkrq, DateUtil.today())) <= 0) {
            throw new FailException("报到失败，考生信息维护失败");
        }
        return true;
    }

    /**
     * 签到
     *
     * @param signInBo signIn
     * @return boolean
     */
    @Override
    public Boolean signIn(SignInBo signInBo) {
        // 更新排队表签到状态
        if (queuingMapper.update(null,
                Wrappers.lambdaUpdate(W2Queuing.class)
                        .eq(W2Queuing::getZjhm, signInBo.getZjhm())
                        .eq(W2Queuing::getKsrq, DateUtil.beginOfDay(new Date()))
                        .set(W2Queuing::getSign, 1l)
        ) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 分车
     *
     * @return {@link List}<{@link String}>
     */
    @Override
    @DSTransactional
    public Boolean splitCar() {
        // 获取可用考车信息
        LambdaQueryWrapper<W2Kcxx> kcLqw = Wrappers.lambdaQuery(W2Kcxx.class);
        kcLqw.eq(W2Kcxx::getZt, "1");
        List<W2KcxxVo> w2KcxxVos = kcxxMapper.selectVoList(kcLqw);
        List<String> cphList = w2KcxxVos.stream().map(W2KcxxVo::getCph).collect(Collectors.toList());
        if (cphList.size() == 0) throw new FailException("没有可分配的车辆");
        Map<String, W2KcxxVo> kcxxMap = w2KcxxVos.stream().collect(Collectors.toMap(W2KcxxVo::getCph, vo -> vo));

        // 相同证型的同一个考生
        List<String> sameStudents = new ArrayList<>();

        // 车牌号对应考生的数量
        HashMap<String, Integer> kchMap = new HashMap<>();
        // 获取待为考试考生
        List<W2QueuingVo> queuingList = queuingMapper.selectVoList(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getSign, 1l)
                        .eq(W2Queuing::getKszt, "0")
                        .eq(W2Queuing::getSqks, 1l)
                        .eq(W2Queuing::getKsrq, DateUtil.beginOfDay(new Date()))
                        .orderByDesc(W2Queuing::getZt)
                        .orderByAsc(W2Queuing::getKskm)
        );

        Iterator<W2QueuingVo> iterator = queuingList.iterator();
        while (iterator.hasNext()) {
            W2QueuingVo next = iterator.next();
            //删除已分车的学员
            if (next.getZt().equals("1")) {
                if (!sameStudents.contains(next.getZjhm())) {
                    sameStudents.add(next.getZjhm());
                    Integer num = kchMap.containsKey(next.getKchp()) ? kchMap.get(next.getKchp()) : 1;
                    kchMap.put(next.getKchp(), num);
                }
                iterator.remove();
            } else {
                if (sameStudents.contains(next.getZjhm())) {
                    iterator.remove();
                } else {
                    sameStudents.add(next.getZjhm());
                }
            }
        }
        // 没有待分的学员，返回
        if (queuingList.size() == 0) return false;
        //打乱排队
        Collections.shuffle(queuingList);

        // 获取最大分车人数
        Long splitNum = Long.valueOf(configService.selectConfigByKey(CacheNames.SPLIT_NUM));

        // 已分车继续分车
        // 处理已分车的车辆继续分车
        kchMap.entrySet().stream().forEach(vo -> {
            if (cphList.contains(vo.getKey()) && splitNum - vo.getValue() > 0) {
                cphList.remove(vo.getKey());

                // 处理分车
                commonHandlerService.handleSplitResult(kcxxMap.get(vo.getKey()), splitNum - vo.getValue(), queuingList);
            }
        });
        // 未分车的车辆首次分车
        if (cphList.size() > 0) {
            cphList.stream().forEach(cph -> {
                commonHandlerService.handleSplitResult(kcxxMap.get(cph), splitNum, queuingList);
            });
        }

        return true;
    }

    /**
     * 申请考试
     *
     * @param queuingVo 排队信息
     * @param kcbh      kcbh
     * @param kssj      kssj
     * @param zjhm      zjhm
     * @param kskm      kskm
     * @param zp        zp
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> applyExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String kskm, String zp) {
        queuingVo.setZkxms(queuingVo.getKsxm());
        return R.ok("无需申请考试");
    }

    /**
     * 结束考试
     *
     * @param queuingVo 排队
     * @param kcbh      kcbh
     * @param kssj      kssj
     * @param zjhm      zjhm
     * @param zp        zp
     * @param score     分数
     * @param speed     速度
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> finishExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String score, String zp, String speed) {
        return R.ok("无需结束考试");
    }

    /**
     * 开始项目
     *
     * @param queuingVo  排队
     * @param kcbh       kcbh
     * @param kssj       kssj
     * @param zjhm       zjhm
     * @param cdxmConfig cdxm配置
     * @param zp         zp
     * @param speed      速度
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> startProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed) {
        return R.ok("无需项目开始");
    }


    /**
     * 结束项目
     *
     * @param queuingVo  排队
     * @param kcbh       kcbh
     * @param kssj       kssj
     * @param zjhm       zjhm
     * @param cdxmConfig cdxm配置
     * @param zp         zp
     * @param speed      速度
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> finishProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed) {
        return R.ok("无需结束项目");
    }

    /**
     * 上传扣分
     *
     * @param queuingVo  排队
     * @param kcbh       kcbh
     * @param kssj       kssj
     * @param zjhm       zjhm
     * @param cdxmConfig cdxm配置
     * @param kfdm       kfdm
     * @param kfType     kf型
     * @param zp         zp
     * @param speed      速度
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> deductPoint(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String kfdm, String kfType, String zp, String speed) {
        return R.ok("无需上传扣分");
    }

    /**
     * 上传图片
     *
     * @param kskm  kskm
     * @param kcbh  kcbh
     * @param kssj  kssj
     * @param zjhm  zjhm
     * @param ksxm  ksxm
     * @param zp    zp
     * @param speed 速度
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> uploadImg(W2QueuingVo queuingVo, String kskm, String kcbh, String kssj, String zjhm, String ksxm, String zp, String speed) {
        return R.ok("图片无需上传");
    }


}
