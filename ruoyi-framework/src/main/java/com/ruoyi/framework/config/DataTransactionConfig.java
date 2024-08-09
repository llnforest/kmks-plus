package com.ruoyi.framework.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Star
 * @description: TODO
 * @date 2024/7/14 23:16
 */
@Configuration
public class DataTransactionConfig {

    @Primary
    @Bean(name = "masterTransactionManager")
    public PlatformTransactionManager masterTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "paramTransactionManager")
    public PlatformTransactionManager paramTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
