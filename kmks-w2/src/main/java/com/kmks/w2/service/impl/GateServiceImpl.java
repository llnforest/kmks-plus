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
import com.kmks.w2.service.impl.supervise.SuperviseHandler;
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
    private final IJgService jgService;

    private final W2RecordsMapper recordsMapper;

    private final W2QueuingMapper queuingMapper;

    private final ImageServiceImpl imageService;

    private final SuperviseHandler superviseHandler;

    /**
     * 处理报到
     *
     * @param checkInBo 报到bo
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    public Boolean handleCheckIn(CheckInBo checkInBo) {
        return superviseHandler.service().checkIn(checkInBo);
    }

    @Override
    public boolean handleSignIn(SignInBo signInBo){
        return superviseHandler.service().signIn(signInBo);
    }

}
