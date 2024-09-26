package com.kmks.w2.service;

import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.ruoyi.common.core.domain.R;

import java.util.Map;

/**
 * @author Star
 * @description: TODO
 * @date 2024/9/3 13:50
 */
public interface ISuperviseService {
    String syncTime();

    Boolean checkIn(CheckInBo checkInBo);

    Boolean signIn(SignInBo signInBo);

    Boolean splitCar();

    R<Void> applyExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String kskm, String zp);

    R<Void> finishExam(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, String score, String zp, String speed);

    R<Void> startProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed);

    R<Void> finishProgram(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String zp, String speed);

    R<Void> deductPoint(W2QueuingVo queuingVo, String kcbh, String kssj, String zjhm, W2CdxmbhVo cdxmConfig, String kfdm, String kfType, String zp, String speed);

    R<Void> uploadImg(W2QueuingVo queuingVo, String kskm, String kcbh, String kssj, String zjhm, String ksxm, String zp, String speed);
}
