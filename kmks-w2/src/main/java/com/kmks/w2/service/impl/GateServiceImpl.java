package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.jianguan.domain.bo.A0221000001Bo;
import com.kmks.jianguan.domain.bo.A0221000002Bo;
import com.kmks.jianguan.domain.bo.A0221000007Bo;
import com.kmks.jianguan.domain.vo.A0221000001Vo;
import com.kmks.jianguan.domain.vo.A0221000002Vo;
import com.kmks.jianguan.domain.vo.A0221000007Vo;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.config.FaceConfig;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.service.IGateService;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 15:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GateServiceImpl implements IGateService {
    private final ISysConfigService configService;
    private final IJgService jgService;

    private final W2RecordsMapper recordsMapper;

    private final W2QueuingMapper queuingMapper;

    private final ImageServiceImpl imageService;

    /**
     * 处理报到
     *
     * @param checkInBo 报到bo
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    public Boolean handleCheckIn(CheckInBo checkInBo) {
        // 判断此人是否在今日考试记录中报到
        if(recordsMapper.exists(
                Wrappers.lambdaQuery(W2Records.class)
                .eq(W2Records::getZjhm,checkInBo.getZjhm())
                .eq(W2Records::getYkrq, DateUtil.today())
        )){
            throw new FailException("考生已报到");
        }
        // 02-21-000002-约考信息查询
        A0221000002Bo a0221000002Bo = new A0221000002Bo();
        a0221000002Bo.setSfzmhm(checkInBo.getZjhm());

        a0221000002Bo.setKskm(checkInBo.getKskm());
        A0221000002Vo a0221000002Vo = jgService.a0221000002(a0221000002Bo);
        // 判断考生是否可用
        isStudentAvailable(a0221000002Vo);
        
        //约考信息录入考试信息
        W2Records w2Records = BeanUtil.toBean(a0221000002Vo, W2Records.class);
        w2Records.setZjhm(checkInBo.getZjhm());
        w2Records.setZjzp(imageService.saveImage(checkInBo.getZjzp()));
        w2Records.setJbzp(imageService.saveImage(checkInBo.getJbzp()));
        w2Records.setKskm(checkInBo.getKskm());
        // 考试项目从分车中获取
//        w2Records.setKsxm(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY) + ","+configService.selectConfigByKey(CacheNames.PROJECT_IDS_KEY));
        w2Records.setKsrq(DateUtil.beginOfDay(new Date()));
        w2Records.setKsjg("0");
        w2Records.setKscj1("0");
        w2Records.setKscj2("0");
        if(StringUtils.isBlank(w2Records.getXm())) w2Records.setXm(checkInBo.getXm());
        w2Records.setYkrq(DateUtil.today());
        w2Records.setKszt("0");
        //考场编号  用 jxdm 字段代替
        w2Records.setJxdm(w2Records.getKcbh());
        w2Records.setKcbh(null);
        if(recordsMapper.insert(w2Records) <= 0){
            throw new FailException("录入考生信息失败");
        }
        //录入排队信息
        W2Queuing w2Queuing = new W2Queuing();
        w2Queuing.setZjhm(checkInBo.getZjhm());
        w2Queuing.setXm(StringUtils.isBlank(a0221000002Vo.getXm())?checkInBo.getXm():a0221000002Vo.getXm());
        w2Queuing.setSqks(a0221000002Vo.getJkbj());
        w2Queuing.setSign(0l);
        w2Queuing.setZt("0");
        w2Queuing.setKszt("0");
        w2Queuing.setDjc(0l);
        w2Queuing.setScore(0l);
        w2Queuing.setKscx(a0221000002Vo.getKscx());
        w2Queuing.setBdxh(0l);
        w2Queuing.setKskm(checkInBo.getKskm());
        w2Queuing.setKsrq(DateUtil.beginOfDay(new Date()));
//        w2Queuing.setKsxm(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY) + ","+configService.selectConfigByKey(CacheNames.PROJECT_IDS_KEY));
        w2Queuing.setKscs(w2Records.getKscs());
        w2Queuing.setYycs(String.valueOf(w2Records.getYycs()));
        w2Queuing.setJxdm(w2Records.getJxdm());
        w2Queuing.setBcyykscs(w2Records.getBcyykscs());
        w2Queuing.setKsyy(w2Records.getKsyy());
        queuingMapper.insert(w2Queuing);

        return true;
    }

    @Override
    public boolean handleSignIn(SignInBo signInBo){
        A0221000007Bo a0221000007Bo = new A0221000007Bo();
        a0221000007Bo.setKskm(signInBo.getKskm());
        a0221000007Bo.setSfzmhm(signInBo.getZjhm());
        A0221000007Vo a0221000007Vo = jgService.a0221000007(a0221000007Bo, signInBo.getZjzp());
        if(a0221000007Vo.getCode().equals("1")){
            // 未缴款 不允许签到
            if(!a0221000007Vo.getRetval().equals("1")){
                throw new FailException("本地签到失败："+(a0221000007Vo.getRetval().equals("0")?"未缴款":a0221000007Vo.getRetval()));
            }
        }else{
            // 其他签到失败状态
            throw new FailException("签到失败："+a0221000007Vo.getCode()+":"+a0221000007Vo.getMessage()+"；"+a0221000007Vo.getRetval());
        }
        // 更新排队表签到状态
        if(queuingMapper.update(null,
                Wrappers.lambdaUpdate(W2Queuing.class)
                        .eq(W2Queuing::getZjhm,signInBo.getZjhm())
                        .eq(W2Queuing::getKsrq,DateUtil.beginOfDay(new Date()))
                        .set(W2Queuing::getSign,1l)
        ) > 0){
            return true;
        }
        return false;

    }

    /**
     * 判断预约信息是否可以使用
     *
     * @param a0221000002Vo a0221000002沃
     * @return {@link Boolean}
     */
    private Boolean isStudentAvailable(A0221000002Vo a0221000002Vo){
        // 判断是否欠费
        if(!a0221000002Vo.getJkbj().equals("1")){
            throw new FailException(a0221000002Vo.getJkbj().equals("2")?"考生未缴款":a0221000002Vo.getJkbj());
        }
        //判断是否是当日约考考生
        if(!a0221000002Vo.getYkrq().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(DateUtil.today())){
            throw new FailException("非当日约考考生");
        }
        return true;
    }
}
