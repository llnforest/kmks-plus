package com.ruoyi.framework.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;

/**
 * @author Star
 * @description: TODO
 * @date 2024/6/25 23:57
 */

public class DataSourceConfig {

    @Value("${spring.datasource.dynamic.datasource.master.url}")
    private String dbUrl;

    @Value("${spring.datasource.dynamic.datasource.master.username}")
    private String dbUsername;

    @Value("${spring.datasource.dynamic.datasource.master.password}")
    private String dbPassword;

    private static final String KEY = "Ghgd.123456789##";

    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        try {
//            数据库aes加密
            String passwrod = "ghgd_admin12345";
            AES aes = SecureUtil.aes(KEY.getBytes(StandardCharsets.UTF_8));

            dataSource.setPassword(aes.decryptStr(passwrod));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}

