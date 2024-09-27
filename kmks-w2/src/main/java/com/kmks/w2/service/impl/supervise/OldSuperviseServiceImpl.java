package com.kmks.w2.service.impl.supervise;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.bo.*;
import com.kmks.jianguan.domain.vo.*;
import com.kmks.jianguanold.domain.bo.*;
import com.kmks.jianguanold.domain.vo.*;
import com.kmks.jianguanold.service.IJgOldService;
import com.kmks.jianguanold.service.impl.JgOldServiceImpl;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.domain.W2Ksy;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.dto.SplitCarServerResponseDto;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.mapper.W2KsyMapper;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: 老专网服务
 * @date 2024/9/3 13:51
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OldSuperviseServiceImpl implements ISuperviseService {
    private final ISysConfigService configService;
    private final W2RecordsMapper recordsMapper;

    private final W2QueuingMapper queuingMapper;


    private final CommonHandlerService commonHandlerService;

    private final IJgOldService jgOldService;

    private final IW2LineconfigService lineconfigService;
    private final IW2CdxmbhService cdxmbhService;

    private final W2KsyMapper ksyMapper;

    /**
     * 同步时间
     */
    @Override
    public String syncTime() {
        try {
            A17C09Bo a17C09Bo = new A17C09Bo();
            a17C09Bo.setXh(configService.selectConfigByKey(CacheNames.JG_COMMON_KSXTXH));
            A17C09Vo a17C09Vo = jgOldService.a17c09(a17C09Bo);
            String[] s = a17C09Vo.getBody().get(0).getSj().split(" ");
            commonHandlerService.syncToLocalTime(a17C09Vo.getBody().get(0).getSj(), s[0], s[1]);
            return a17C09Vo.getBody().get(0).getSj();
        } catch (Exception exception) {
            log.info("获取监管时间失败");
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean checkIn(CheckInBo checkInBo) {
        // 提前处理
        boolean onlyCheckJg = commonHandlerService.beforeCheckIn(checkInBo);

        A17C08Bo a17C08Bo = new A17C08Bo();
        a17C08Bo.setKskm(checkInBo.getKskm());
        a17C08Bo.setKsrq(DateUtil.today());
        a17C08Bo.setKsdd(configService.selectConfigByKey(CacheNames.JG_OLD_KSDD));
        a17C08Bo.setSfzmhm(checkInBo.getZjhm());
        A17C08Vo a17C08Vo = null;

        try {
            a17C08Vo = jgOldService.a17c08(a17C08Bo);
        } catch (FailException failException) {
            if (onlyCheckJg) throw failException;
            else throw new SuccessException("单独科三报道成功");
        }

        // 判断考生是否缴费
        A17CC1Bo a17CC1Bo = new A17CC1Bo();
        a17CC1Bo.setKskm(checkInBo.getKskm());
        a17CC1Bo.setSfzmhm(checkInBo.getZjhm());
        A17CC1Vo a17CC1Vo = jgOldService.a17cc1(a17CC1Bo);
        if (!a17CC1Vo.getHead().getCode().equals("1") && configService.selectConfigByKey(CacheNames.SYS_CONFIG_NEED_FEE).equals("1")) {
            throw new FailException("考生未缴费");
        }

        //约考信息录入考试信息
        W2Records w2Records = BeanUtil.toBean(a17C08Vo.getBody().get(0), W2Records.class);
        w2Records.setZjhm(a17C08Vo.getBody().get(0).getSfzmhm());
        w2Records.setSZjmc(a17C08Vo.getBody().get(0).getSfzmmc());
        w2Records.setZkzh(a17C08Vo.getBody().get(0).getZkzmbh());
        //生成考生记录
        commonHandlerService.checkInCreateW2Records(w2Records, checkInBo);

        //录入排队信息
        W2Queuing w2Queuing = new W2Queuing();
        // 分配考试员，去查找证件号码（优化成保存成考试员证件号码）
        W2KsyVo w2KsyVo = ksyMapper.selectVoOne(Wrappers.lambdaQuery(W2Ksy.class).eq(W2Ksy::getXm, w2Records.getKsy1()));
        if (w2KsyVo == null) {
            throw new FailException("考试员【" + w2Records.getKsy1() + "】信息未下载或不存在");
        } else {
            w2Queuing.setKg(w2Records.getKsy1());
            w2Queuing.setKg2(w2Records.getKsy2());
            w2Queuing.setKgname(w2KsyVo.getSfzmhm());
        }
        w2Queuing.setSqks(a17C08Vo.getBody().get(0).getJkbj());
        w2Queuing.setLsh(w2Records.getLsh());
        //生成排队记录
        commonHandlerService.checkInCreateW2Queuing(w2Queuing, w2Records);
        return true;
    }

    @Override
    public Boolean signIn(SignInBo signInBo) {
        // 提前处理
        boolean onlyCheckJg = commonHandlerService.beforeSignIn(signInBo);

        W2RecordsVo w2RecordsVo = recordsMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Records.class)
                        .eq(W2Records::getZjhm, signInBo.getZjhm())
                        .eq(W2Records::getKskm, signInBo.getKskm())
                        .eq(W2Records::getYkrq, DateUtil.today())
        );
        if (w2RecordsVo == null) {
            if (!onlyCheckJg) {
                throw new SuccessException("单独科三签到成功");
            }
            throw new FailException("考生不存在");
        }
        A17CB2Bo a17CB2Bo = new A17CB2Bo();
        a17CB2Bo.setSfzmhm(w2RecordsVo.getZjhm());
        a17CB2Bo.setKskm(signInBo.getKskm());
        a17CB2Bo.setKcxh(w2RecordsVo.getKcxh());
        a17CB2Bo.setKscc(w2RecordsVo.getKscc());
        A17CB2Vo a17CB2Vo = null;
        try {
            a17CB2Vo = jgOldService.a17cb2(a17CB2Bo);
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

    @Override
    public Boolean splitCar() {
        //分车前通用
        SplitCarDto splitCarDto = commonHandlerService.beforeSplitCar();

        //处理已分车的车辆继续分车
        splitCarDto.getKchMap().entrySet().stream().forEach(vo -> {
            if (splitCarDto.getCphList().contains(vo.getKey()) && splitCarDto.getSplitNum() - vo.getValue() > 0) {
                splitCarDto.getCphList().remove(vo.getKey());
                // 发送分车指令
                SplitCarServerResponseDto responseDto = sendSplitCar(vo.getKey(), String.valueOf(splitCarDto.getSplitNum() - vo.getValue()), splitCarDto.getKscc(), splitCarDto.getKgzjh());
                commonHandlerService.handleSplitResult(splitCarDto.getKcxxMap().get(vo.getKey()), responseDto, splitCarDto.getLineConfigMap(), splitCarDto.getSplitNum() - vo.getValue());
            }
        });

        // 未分车的车辆首次分车
        if (splitCarDto.getCphList().size() > 0) {
            splitCarDto.getCphList().stream().forEach(cph -> {
                // 发送分车指令
                SplitCarServerResponseDto responseDto = sendSplitCar(cph, String.valueOf(splitCarDto.getSplitNum()), splitCarDto.getKscc(), splitCarDto.getKgzjh());
                commonHandlerService.handleSplitResult(splitCarDto.getKcxxMap().get(cph), responseDto, splitCarDto.getLineConfigMap(), splitCarDto.getSplitNum());
            });
        }

        return true;

    }

    @Override
    public R<Void> applyExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String kskm, String zp) {
        // 发送开始考试至监管
        A17C51Bo a17C51Bo = new A17C51Bo();
        a17C51Bo.setLsh(queuingVo.getLsh());
        a17C51Bo.setKskm(kskm);
        a17C51Bo.setKsxtbh(configService.selectConfigByKey(CacheNames.JG_COMMON_KSXTXH));
        a17C51Bo.setSfzmhm(zjhm);
        a17C51Bo.setKsysfzmhm(queuingVo.getKgname());
        a17C51Bo.setKchp(queuingVo.getKchp());
        a17C51Bo.setZp(zp);
        a17C51Bo.setKssj(kssj);
        if (kskm.equals("3")) {
            a17C51Bo.setKsxl(lineconfigService.getLineDm(queuingVo.getRLine()));
        }
        A17C51Vo a17C51Vo = jgOldService.a17c51(a17C51Bo);

        //更新监管剩余项目
        String zkxm = cdxmbhService.getMdmByGadm(new ArrayList<>(Arrays.asList(a17C51Vo.getHead().getKeystr().split(","))));
        queuingVo.setZkxms(zkxm);
        return R.ok();
    }

    @Override
    public R<Void> finishExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String score, String zp, String speed) {
        // 上传考试结束至监管
        A17C56Bo a17C56Bo = new A17C56Bo();
        a17C56Bo.setLsh(queuingVo.getLsh());
        a17C56Bo.setKskm(queuingVo.getKskm());
        a17C56Bo.setSfzmhm(zjhm);
        a17C56Bo.setZp(zp);
        a17C56Bo.setJssj(kssj);
        a17C56Bo.setKscj(score);
        A17C56Vo a17C56Vo = jgOldService.a17c56(a17C56Bo);
        return R.ok();
    }

    @Override
    public R<Void> startProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed) {
        // 上传项目开始至监管
        A17C52Bo a17C52Bo = new A17C52Bo();
        a17C52Bo.setLsh(queuingVo.getLsh());
        a17C52Bo.setKskm(queuingVo.getKskm());
        a17C52Bo.setSfzmhm(zjhm);
        a17C52Bo.setKsxm(cdxmConfig.getGadm());
        a17C52Bo.setKchp(queuingVo.getKchp());

        if (a17C52Bo.getKskm().equals("2")) {
            a17C52Bo.setSbxh(cdxmConfig.getSbxh());
        } else {
            String lineDm = lineconfigService.getLineDm(queuingVo.getRLine());
            a17C52Bo.setKsxl(lineDm);
        }
        a17C52Bo.setKssj(kssj);
        A17C52Vo a17C52Vo = jgOldService.a17c52(a17C52Bo);

        return R.ok();
    }

    @Override
    public R<Void> finishProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed) {
        // 上传考试结束至监管
        A17C55Bo a17C55Bo = new A17C55Bo();
        a17C55Bo.setLsh(queuingVo.getLsh());
        a17C55Bo.setKskm(queuingVo.getKskm());
        a17C55Bo.setSfzmhm(zjhm);
        a17C55Bo.setKsxm(cdxmConfig.getGadm());
        if (a17C55Bo.getKskm().equals("2")) {
            a17C55Bo.setSbxh(cdxmConfig.getSbxh());
        }
        a17C55Bo.setJssj(kssj);
        a17C55Bo.setCzlx("1");
        A17C55Vo a17C55Vo = jgOldService.a17c55(a17C55Bo);

        return R.ok();
    }

    @Override
    public R<Void> deductPoint(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String kfdm, String kfType, String zp, String speed) {
        // 上传考试扣分至监管
        A17C53Bo a17C53Bo = new A17C53Bo();
        a17C53Bo.setLsh(queuingVo.getLsh());
        a17C53Bo.setKskm(queuingVo.getKskm());
        a17C53Bo.setKsxm(cdxmConfig.getGadm());
        a17C53Bo.setKfxm(kfdm);
        a17C53Bo.setSfzmhm(zjhm);
        a17C53Bo.setKfsj(kssj);
        A17C53Vo a17C53Vo = jgOldService.a17c53(a17C53Bo);

        return R.ok();
    }

    @Override
    public R<Void> uploadImg(W2QueuingVo queuingVo, String kskm, String kcbh, String kssj, String zjhm, String ksxm, String zp, String speed) {
        // 上传考试过程图片至监管
        A17C54Bo a17C54Bo = new A17C54Bo();
        a17C54Bo.setLsh(queuingVo.getLsh());
        a17C54Bo.setKskm(kskm);
        a17C54Bo.setKsxm(ksxm);
        a17C54Bo.setZpsj(kssj);
        a17C54Bo.setSfzmhm(zjhm);
        a17C54Bo.setZp(zp);
        a17C54Bo.setCs(speed);
        A17C54Vo a17C54Vo = jgOldService.a17c54(a17C54Bo);

        return R.ok();
    }

    /**
     * 发送分车请求
     *
     * @param kchp     考车号牌
     * @param splitNum 分配人数
     * @param kscc     考试场次
     * @param kgzjh    考试员证件号码
     * @return {@link SplitCarServerResponseDto}
     */
    private SplitCarServerResponseDto sendSplitCar(String kchp, String splitNum, Long kscc, String kgzjh) {
        A17CB3Bo a17CB3Bo = new A17CB3Bo();
        a17CB3Bo.setKchp(kchp);
        a17CB3Bo.setSjrs(splitNum);
        a17CB3Bo.setKscc(String.valueOf(kscc));

        // 摩托车考场必传
        if (configService.selectConfigByKey(CacheNames.COURSE_KEY).equals("2,3")) {
            a17CB3Bo.setKsysfzmhm(kgzjh);
        }
        a17CB3Bo.setKcxh(configService.selectConfigByKey(CacheNames.JG_OLD_KCXH));
        A17CB3Vo a17CB3Vo = jgOldService.a17cb3(a17CB3Bo);
        SplitCarServerResponseDto responseDto = new SplitCarServerResponseDto();
        responseDto.setCode(a17CB3Vo.getHead().getCode());
        responseDto.setMessage(a17CB3Vo.getHead().getMessage());
        responseDto.setRetval(a17CB3Vo.getHead().getKeystr());
        return responseDto;
    }
}
