package com.kmks.w2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Star
 * @description: TODO
 * @date 2024/2/23 14:38
 */
@Component
public class FaceConfig {
    public static String appId;
    public static String sdkKey;
    public static String enginePath;
    public static String storagePath;
    public static Float score;

    @Value("${arcFace.appId}")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Value("${arcFace.sdkKey}")
    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    @Value("${arcFace.enginePath}")
    public void setEnginePath(String enginePath) {
        this.enginePath = enginePath;
    }

    @Value("${arcFace.score}")
    public void setScore(Float score) {
        this.score = score;
    }

    @Value("${arcFace.storagePath}")
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }




}
