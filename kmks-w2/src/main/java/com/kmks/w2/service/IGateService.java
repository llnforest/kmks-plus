package com.kmks.w2.service;

import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 15:53
 */
public interface IGateService {

    @Transactional
    Boolean handleCheckIn(CheckInBo checkInBo);

    boolean handleSignIn(SignInBo signInBo);
}
