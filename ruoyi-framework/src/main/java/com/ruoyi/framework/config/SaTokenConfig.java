package com.ruoyi.framework.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.dto.UserOnlineDTO;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.framework.config.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * sa-token 配置
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;
    private final cn.dev33.satoken.config.SaTokenConfig tokenConfig;


    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 登录验证 -- 排除多个路径
            SaRouter
                // 获取所有的
                .match("/**")
                // 对未排除的路径进行检查
                .check(() -> {
                    // 检查是否登录 是否有token
                    String tokenValue = StpUtil.getTokenValue();
                    UserOnlineDTO dto = (UserOnlineDTO) RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
                    try{
                        StpUtil.checkLogin();
                    }catch (NotLoginException ex){
                        //删除唯一在线用户 和 token
                        RedisUtils.deleteObject(CacheConstants.ONLINE_SINGLE_KEY + dto.getUserName());
                        RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
                        throw ex;
                    }
                    RedisUtils.setCacheObject(CacheConstants.ONLINE_SINGLE_KEY + dto.getUserName(),1, Duration.ofSeconds(tokenConfig.getActivityTimeout()));

                    // 有效率影响 用于临时测试
                    // if (log.isDebugEnabled()) {
                    //     log.debug("剩余有效时间: {}", StpUtil.getTokenTimeout());
                    //     log.debug("临时有效时间: {}", StpUtil.getTokenActivityTimeout());
                    // }

                });
        })).addPathPatterns("/**")
            // 排除不需要拦截的路径
            .excludePathPatterns(securityProperties.getExcludes());
    }

    @Bean
    public StpLogic getStpLogicJwt() {
        // Sa-Token 整合 jwt (简单模式)
        return new StpLogicJwtForSimple();
    }

}
