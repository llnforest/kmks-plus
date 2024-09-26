package com.kmks.w2.service.impl.supervise;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.bo.*;
import com.kmks.jianguan.domain.vo.*;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.dto.SplitCarServerResponseDto;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.service.ISuperviseService;
import com.kmks.w2.service.IW2CdxmbhService;
import com.kmks.w2.service.IW2LineconfigService;
import com.kmks.w2.service.impl.ImageServiceImpl;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.exception.api.SuccessException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Star
 * @description: 新专网服务
 * @date 2024/9/3 13:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NewSuperviseServiceImpl implements ISuperviseService {
    private final IJgService jgService;

    private final W2RecordsMapper recordsMapper;

    private final W2QueuingMapper queuingMapper;

    private final W2KcxxMapper kcxxMapper;

    private final ImageServiceImpl imageService;

    private final ISysConfigService configService;

    private final IW2LineconfigService lineconfigService;

    private final IW2CdxmbhService cdxmbhService;

    private final CommonHandlerService commonHandlerService;

    /**
     * 同步时间
     */
    @Override
    public String syncTime() {
        try {
            A0221000006Bo a0221000006Bo = new A0221000006Bo();
            a0221000006Bo.setKsxtxh(configService.selectConfigByKey(CacheNames.JG_COMMON_KSXTXH));
            A0221000006Vo a0221000006Vo = jgService.a0221000006(a0221000006Bo);
            String dateTime = a0221000006Vo.getSj().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            commonHandlerService.syncToLocalTime(dateTime, a0221000006Vo.getSj().format(DateTimeFormatter.ISO_LOCAL_DATE), a0221000006Vo.getSj().format(DateTimeFormatter.ISO_LOCAL_TIME));
            return dateTime;
        } catch (Exception exception) {
            log.info("获取监管时间失败");
        }
        return null;
    }

    /**
     * 报到
     *
     * @param checkInBo checkInBo
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    public Boolean checkIn(CheckInBo checkInBo) {
        boolean onlyCheckJg = commonHandlerService.beforeCheckIn(checkInBo);

        // 02-21-000002-约考信息查询
        A0221000002Bo a0221000002Bo = new A0221000002Bo();
        a0221000002Bo.setSfzmhm(checkInBo.getZjhm());
        a0221000002Bo.setKskm(checkInBo.getKskm());
        A0221000002Vo a0221000002Vo = null;
        try {
            a0221000002Vo = jgService.a0221000002(a0221000002Bo);
        } catch (FailException failException) {
            if (onlyCheckJg) throw failException;
            else throw new SuccessException("单独科三报道成功");
        }

        // 判断考生是否可用
        isStudentAvailable(a0221000002Vo);

        //约考信息录入考试信息
        W2Records w2Records = BeanUtil.toBean(a0221000002Vo, W2Records.class);
        //生成考生记录
        commonHandlerService.checkInCreateW2Records(w2Records, checkInBo);

        //录入排队信息
        W2Queuing w2Queuing = new W2Queuing();
        w2Queuing.setSqks(a0221000002Vo.getJkbj());
        //生成排队记录
        commonHandlerService.checkInCreateW2Queuing(w2Queuing, w2Records);
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
        //提前处理
        boolean onlyCheckJg = commonHandlerService.beforeSignIn(signInBo);

        A0221000007Bo a0221000007Bo = new A0221000007Bo();
        a0221000007Bo.setKskm(signInBo.getKskm());
        a0221000007Bo.setSfzmhm(signInBo.getZjhm());
        A0221000007Vo a0221000007Vo = null;
        try {
            a0221000007Vo = jgService.a0221000007(a0221000007Bo, signInBo.getZjzp());
        } catch (FailException failException) {
            if (onlyCheckJg) throw failException;
            else throw new SuccessException("单独科三签到成功");
        }

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
        //分车前通用
        SplitCarDto splitCarDto = commonHandlerService.beforeSplitCar();

        //处理已分车的车辆继续分车
        splitCarDto.getKchMap().entrySet().stream().forEach(vo -> {
            if (splitCarDto.getCphList().contains(vo.getKey()) && splitCarDto.getSplitNum() - vo.getValue() > 0) {
                splitCarDto.getCphList().remove(vo.getKey());
                // 发送分车指令
                SplitCarServerResponseDto responseDto = sendSplitCar(vo.getKey(), String.valueOf(splitCarDto.getSplitNum() - vo.getValue()));
                commonHandlerService.handleSplitResult(splitCarDto.getKcxxMap().get(vo.getKey()), responseDto, splitCarDto.getLineConfigMap(), splitCarDto.getSplitNum() - vo.getValue());
            }
        });
        // 未分车的车辆首次分车
        if (splitCarDto.getCphList().size() > 0) {
            splitCarDto.getCphList().stream().forEach(cph -> {
                // 发送分车指令
                SplitCarServerResponseDto responseDto = sendSplitCar(cph, String.valueOf(splitCarDto.getSplitNum()));
                commonHandlerService.handleSplitResult(splitCarDto.getKcxxMap().get(cph), responseDto, splitCarDto.getLineConfigMap(), splitCarDto.getSplitNum());
            });
        }

        return true;
    }

    /**
     * 申请考试
     *
     * @param queuingVo 排队
     * @param kcbh      kcbh
     * @param kssj      kssj
     * @param zjhm      zjhm
     * @param kskm      kskm
     * @param zp        zp
     * @return {@link R}<{@link Void}>
     */
    @Override
    public R<Void> applyExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String kskm, String zp) {
        // 发送开始考试至监管
        A0221000009Bo a0221000009Bo = new A0221000009Bo();
        a0221000009Bo.setSfzmhm(zjhm);
        a0221000009Bo.setKchp(queuingVo.getKchp());
        a0221000009Bo.setKskm(kskm);
        a0221000009Bo.setKssj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (kskm.equals("3")) {
            a0221000009Bo.setKsxl(lineconfigService.getLineDm(queuingVo.getRLine()));
        }
        A0221000009Vo a0221000009Vo = jgService.a0221000009(a0221000009Bo, zp);
        //更新监管剩余项目
        String zkxm = cdxmbhService.getMdmByGadm(new ArrayList<>(Arrays.asList(a0221000009Vo.getRetval().split(","))));
        queuingVo.setZkxms(zkxm);
        return R.is(a0221000009Vo.getCode().equals("1"), String.format("监管开始考试失败：%s；%s；%s", a0221000009Vo.getCode(), a0221000009Vo.getMessage(), a0221000009Vo.getRetval()));
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
        // 上传考试结束至监管
        A0221000014Bo a0221000014Bo = new A0221000014Bo();
        a0221000014Bo.setSfzmhm(zjhm);
        a0221000014Bo.setKskm(queuingVo.getKskm());
        a0221000014Bo.setJssj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000014Bo.setKscj(score);
        A0221000014Vo a0221000014Vo = jgService.a0221000014(a0221000014Bo, zp);

        return R.is(a0221000014Vo.getCode().equals("1"), String.format(String.format("上传监管考试结束失败：%s；%s", a0221000014Vo.getCode(), a0221000014Vo.getMessage())));
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
        // 上传项目开始至监管
        A0221000010Bo a0221000010Bo = new A0221000010Bo();
        a0221000010Bo.setSfzmhm(zjhm);
        a0221000010Bo.setKchp(queuingVo.getKchp());
        a0221000010Bo.setKskm(queuingVo.getKskm());
        a0221000010Bo.setKsxm(cdxmConfig.getGadm());

        if (a0221000010Bo.getKskm().equals("2")) {
            a0221000010Bo.setSbbh(cdxmConfig.getSbxh());
        } else {
            String lineDm = lineconfigService.getLineDm(queuingVo.getRLine());
            a0221000010Bo.setKsxl(lineDm);
        }
        a0221000010Bo.setKssj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        A0221000010Vo a0221000010Vo = jgService.a0221000010(a0221000010Bo);

        return R.is(a0221000010Vo.getCode().equals("1"), String.format("上传监管项目开始失败：%s；%s", a0221000010Vo.getCode(), a0221000010Vo.getMessage()));
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
        // 上传考试结束至监管
        A0221000013Bo a0221000013Bo = new A0221000013Bo();
        a0221000013Bo.setSfzmhm(zjhm);
        a0221000013Bo.setKskm(queuingVo.getKskm());
        a0221000013Bo.setKsxm(cdxmConfig.getGadm());
        if (a0221000013Bo.getKskm().equals("2")) {
            a0221000013Bo.setSbbh(cdxmConfig.getSbxh());
        } else {
            String lineDm = lineconfigService.getLineDm(queuingVo.getRLine());
            a0221000013Bo.setKsxl(lineDm);
        }
        a0221000013Bo.setJssj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000013Bo.setCzlx("1");
        A0221000013Vo a0221000013Vo = jgService.a0221000013(a0221000013Bo);

        return R.is(a0221000013Vo.getCode().equals("1"), String.format(String.format("上传监管项目结束失败：%s；%s", a0221000013Vo.getCode(), a0221000013Vo.getMessage())));
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
        // 上传考试扣分至监管
        A0221000011Bo a0221000011Bo = new A0221000011Bo();
        a0221000011Bo.setSfzmhm(zjhm);
        a0221000011Bo.setKskm(queuingVo.getKskm());
        a0221000011Bo.setKfsj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000011Bo.setKsxm(cdxmConfig.getGadm());
        a0221000011Bo.setKfxm(kfdm);
        a0221000011Bo.setKffs(kfType);
        A0221000011Vo a0221000011Vo = jgService.a0221000011(a0221000011Bo);

        return R.is(a0221000011Vo.getCode().equals("1"), String.format(String.format("上传监管考试扣分失败：%s；%s", a0221000011Vo.getCode(), a0221000011Vo.getMessage())));
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
        // 上传考试过程图片至监管
        A0221000012Bo a0221000012Bo = new A0221000012Bo();
        a0221000012Bo.setKskm(kskm);
        a0221000012Bo.setZpsj(LocalDateTime.parse(kssj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        a0221000012Bo.setSfzmhm(zjhm);
        a0221000012Bo.setKsxm(ksxm);
        a0221000012Bo.setCs(Double.valueOf(speed));
        A0221000012Vo a0221000012Vo = jgService.a0221000012(a0221000012Bo, zp);

        return R.is(a0221000012Vo.getCode().equals("1"), String.format(String.format("上传监管考试过程图片失败：%s；%s", a0221000012Vo.getCode(), a0221000012Vo.getMessage())));
    }

    /**
     * 发送分车请求
     *
     * @param kchp     考车号牌
     * @param splitNum 分配人数
     * @return {@link SplitCarServerResponseDto}
     */
    private SplitCarServerResponseDto sendSplitCar(String kchp, String splitNum) {
        A0221000008Bo a0221000008Bo = new A0221000008Bo();
        a0221000008Bo.setKchp(kchp);
        a0221000008Bo.setSjrs(splitNum);
        A0221000008Vo a0221000008Vo = jgService.a0221000008(a0221000008Bo);
        return BeanUtil.toBean(a0221000008Vo, SplitCarServerResponseDto.class);
    }

    /**
     * 判断预约信息是否可以使用
     *
     * @param a0221000002Vo a0221000002沃
     * @return {@link Boolean}
     */
    private Boolean isStudentAvailable(A0221000002Vo a0221000002Vo) {
        // 判断是否欠费
        if (!a0221000002Vo.getJkbj().equals("1") && configService.selectConfigByKey(CacheNames.SYS_CONFIG_NEED_FEE).equals("1")) {
            throw new FailException(a0221000002Vo.getJkbj().equals("2") ? "考生未缴款" : a0221000002Vo.getJkbj());
        }
        //判断是否是当日约考考生
        if (!a0221000002Vo.getYkrq().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(DateUtil.today())) {
            throw new FailException("非当日约考考生");
        }
        return true;
    }
}
