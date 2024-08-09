package com.kmks.w2.advice;

import com.kmks.w2.service.SafeLoginService;
import com.ruoyi.common.annotation.LoginResult;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.RequestUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Star
 * @description: TODO
 * @date 2023/3/2 11:12
 */
@ControllerAdvice
@Slf4j
public class LoginResponseBody implements ResponseBodyAdvice<R<T>> {

    @Autowired
    private SafeLoginService safeLoginService;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        Method method = methodParameter.getMethod();
        return method.isAnnotationPresent(LoginResult.class);
    }

    @Override
    public R<T> beforeBodyWrite(R<T> restResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        log.info("调用限制拦截器:{}",restResult.toString());
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)serverHttpRequest;
        Map<String,Object> jsonMap = RequestUtils.getMapByRequestJson(servletRequest.getServletRequest());
        //清除错误次数
        safeLoginService.clearErrorTimes(jsonMap.get("username").toString());
        //记录最后登录时间
        String lastLoginKey = CacheConstants.LAST_LOGIN_KEY + jsonMap.get("username");
        RedisUtils.setCacheObject(lastLoginKey,System.currentTimeMillis());

        return restResult;
    }
}
