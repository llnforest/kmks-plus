package com.kmks.w2.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import com.kmks.jianguan.domain.bo.A0221000001Bo;
import com.kmks.jianguan.domain.vo.A0221000001Vo;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.config.FaceConfig;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.service.IFaceService;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 15:56
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements IFaceService {
    private final ISysConfigService configService;
    private final IJgService jgService;


    /**
     * 人脸识别
     *
     * @param zjzp zjzp
     * @param jbzp jbzp
     * @param zjhm zjhm
     * @return {@link Boolean}
     */
    @Override
    public Boolean faceRecognize(MultipartFile zjzp, MultipartFile jbzp, String zjhm){
        // 监管人脸识别
        FaceRecognizeBo faceRecognizeBo = new FaceRecognizeBo();
        faceRecognizeBo.setZjzp(FileUtil.convertToBase64(zjzp));
        faceRecognizeBo.setJbzp(FileUtil.convertToBase64(jbzp));
        faceRecognizeBo.setZjhm(zjhm);
        return faceRecognize(faceRecognizeBo);
    }

    /**
     * 人脸识别
     *
     * @param faceRecognizeBo 人脸识别bo
     * @return {@link Boolean}
     */
    @Override
    public Boolean faceRecognize(FaceRecognizeBo faceRecognizeBo){
        Boolean similar = false;
        if(configService.selectConfigByKey(CacheNames.FACE_KEY).equals("1")){
            // 监管人脸识别
            similar = handleFaceRecognize(faceRecognizeBo);

        }else if(configService.selectConfigByKey(CacheNames.FACE_KEY).equals("2")){
            // 虹软人脸识别
            similar = hrFaceRecognize(faceRecognizeBo.getZjzp(),faceRecognizeBo.getJbzp());
        }else if(configService.selectConfigByKey(CacheNames.FACE_KEY).equals("3")){
            // 海康人脸识别

        }
        return similar;
    }

    /**
     * 人脸识别（虹软）
     *
     * @param zjzp zjzp
     * @param jbzp jbzp
     * @return {@link Boolean}
     */
    private Boolean hrFaceRecognize(MultipartFile zjzp, MultipartFile jbzp){
        String zjzpPath = FaceConfig.storagePath+ RandomUtil.randomString(12)+".jpg";
        String jbzpPath = FaceConfig.storagePath+RandomUtil.randomString(12)+".jpg";
        File zjzpSource1 = FileUtil.MultipartFileConvertToFile(zjzpPath, zjzp);
        File jbzpSource2 = FileUtil.MultipartFileConvertToFile(jbzpPath, jbzp);
        Boolean similar = similar(zjzpSource1, jbzpSource2);
        FileUtil.deleteImage(zjzpPath,jbzpPath);
        return similar;
    }

    /**
     * 人脸识别（虹软）
     *
     * @param zjzp zjzp
     * @param jbzp jbzp
     * @return {@link Boolean}
     */
    private Boolean hrFaceRecognize(String zjzp, String jbzp){
        String zjzpPath = FaceConfig.storagePath+ RandomUtil.randomString(12)+".jpg";
        String jbzpPath = FaceConfig.storagePath+RandomUtil.randomString(12)+".jpg";
        File zjzpSource1 = FileUtil.base64ConvertToFile(zjzp, zjzpPath);
        File jbzpSource2 = FileUtil.base64ConvertToFile(jbzp, jbzpPath);
        Boolean similar = similar(zjzpSource1, jbzpSource2);
        FileUtil.deleteImage(zjzpPath,jbzpPath);
        return similar;
    }

    /**
     * 人脸识别（虹软）
     *
     * @param file1 文件1
     * @param file2 文件2
     * @return {@link Boolean}
     */
    private Boolean similar(File file1, File file2) {
        FaceEngine faceEngine = new FaceEngine(FaceConfig.enginePath);
        try {
            //激活引擎
            int errorCode = faceEngine.activeOnline(FaceConfig.appId, FaceConfig.sdkKey);

            if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                return closeEngineFailure(faceEngine, "引擎激活失败:" + errorCode);
            }


            ActiveFileInfo activeFileInfo = new ActiveFileInfo();
            errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
            if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                return closeEngineFailure(faceEngine, "获取激活文件信息失败:" + errorCode);
            }

            //引擎配置
            EngineConfiguration engineConfiguration = new EngineConfiguration();
            engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
            engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
            engineConfiguration.setDetectFaceMaxNum(10);
            engineConfiguration.setDetectFaceScaleVal(16);
            //功能配置
            FunctionConfiguration functionConfiguration = new FunctionConfiguration();
            functionConfiguration.setSupportAge(true);
            functionConfiguration.setSupportFace3dAngle(true);
            functionConfiguration.setSupportFaceDetect(true);
            functionConfiguration.setSupportFaceRecognition(true);
            functionConfiguration.setSupportGender(true);
            functionConfiguration.setSupportLiveness(true);
            functionConfiguration.setSupportIRLiveness(true);
            engineConfiguration.setFunctionConfiguration(functionConfiguration);


            //初始化引擎
            errorCode = faceEngine.init(engineConfiguration);

            if (errorCode != ErrorInfo.MOK.getValue()) {
                return closeEngineFailure(faceEngine, "初始化引擎失败:" + errorCode);
            }


            //人脸检测1
            ImageInfo imageInfo = getRGBData(file1);
            List<FaceInfo> faceInfoList = new ArrayList<>();
            errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
            if (faceInfoList.size() == 0) {
                return closeEngineFailure(faceEngine, "未检测出人脸特征1");
            }
            //特征提取1
            FaceFeature faceFeature = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);

            //人脸检测2
            ImageInfo imageInfo2 = getRGBData(file2);
            List<FaceInfo> faceInfoList2 = new ArrayList<>();
            errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2);
            if (faceInfoList2.size() == 0) {
                return closeEngineFailure(faceEngine, "未检测出人脸特征2");
            }
            //特征提取2
            FaceFeature faceFeature2 = new FaceFeature();
            errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
            //        log.info("2特征值大小：" + faceFeature2.getFeatureData().length);

            //特征比对
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
            FaceFeature sourceFaceFeature = new FaceFeature();
            sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
            FaceSimilar faceSimilar = new FaceSimilar();
            errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
            float similarScore = faceSimilar.getScore();
            log.info("Similar：{}", similarScore);
            //引擎卸载
            closeEngine(faceEngine);

            return similarScore >= FaceConfig.score;
        } catch (Exception e) {
            return closeEngineFailure(faceEngine, e.getMessage());
        }
    }

    public Boolean closeEngineFailure(FaceEngine faceEngine, String msg) {
        log.error(msg);
        //引擎卸载
        closeEngine(faceEngine);
        return false;
    }

    public void closeEngine(FaceEngine faceEngine) {
        //引擎卸载
        faceEngine.unInit();
    }

    /**
     * 处理人脸识别(监管)
     *
     * @param faceRecognizeBo 人脸识别bo
     * @return {@link Boolean}
     */
    @Override
    public Boolean handleFaceRecognize(FaceRecognizeBo faceRecognizeBo){
        A0221000001Bo a0221000001Bo = new A0221000001Bo();
        a0221000001Bo.setSfzmhm(faceRecognizeBo.getZjhm());
        A0221000001Vo a0221000001Vo = jgService.a0221000001(a0221000001Bo, faceRecognizeBo.getZjzp(), faceRecognizeBo.getJbzp());
        if(a0221000001Vo.getCode().equals("1")){
            return true;
        }else{
            throw new FailException("比对失败："+a0221000001Vo.getCode()+":"+a0221000001Vo.getMessage()+"；"+a0221000001Vo.getRetval());
        }
    }
}
