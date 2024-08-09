package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/6 17:06
 */
@Component
public class WebServiceConfig {
    public static Boolean enabled;
    public static String url;

    public static String targetNamespace;

    public static String key;


    @Value("${webService.url}")
    public void setUrl(String url) {
        WebServiceConfig.url = url;
    }

    @Value("${webService.targetNamespace}")
    public void setTargetNamespace(String targetNamespace) {
        WebServiceConfig.targetNamespace = targetNamespace;
    }

    @Value("${webService.key}")
    public void setKey(String key) {
        WebServiceConfig.key = key;
    }

    @Value("${webService.enabled}")
    public void setEnabled(Boolean enabled) {
        WebServiceConfig.enabled = enabled;
    }

}
