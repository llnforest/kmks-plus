package com.kmks.w2.service;

import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 15:53
 */
public interface IFaceService {
    Boolean faceRecognize(MultipartFile zjzp, MultipartFile jbzp, String zjhm);

    Boolean faceRecognize(FaceRecognizeBo faceRecognizeBo);

    Boolean handleFaceRecognize(FaceRecognizeBo faceRecognizeBo);
}
