package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.ruoyi.common.core.domain.R;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 15:17
 */
public interface ICarService {
    W2KcxxVo carOnLine(String kcbh);

    W2QueuingVo getNextStudent(String kcbh);

    R<Void> getFaceRecognizeResult(String zjhm, String pzzp);

    W2RecordsVo getTodayStudent(String zjhm);

    W2QueuingVo applyExam(String kcbh, String kssj,String zjhm,String kskm,String zp);

    W2QueuingVo startExam(String kcbh, String kssj, String zjhm, String zp,String speed);

    String startProgram(String kcbh, String kssj, String zjhm, String fieldId, String zp, String speed);


    W2QueuingVo finishProgram(String kcbh, String kssj, String zjhm, String fieldId, String zp, String speed);

    W2QueuingVo finishExam(String kcbh, String kssj, String zjhm, String score, String zp, String speed);

    W2QueuingVo deductPoint(String kcbh, String kssj, String zjhm, String fieldId, String kfdm, String kfType, String zp, String speed);
}
