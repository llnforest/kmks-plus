package com.ruoyi.framework.interceptor;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.kmks.w2.service.SafeLoginService;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.dto.SysMenuDTO;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.filter.RepeatedlyRequestWrapper;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.RequestUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.common.utils.redis.CacheUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.vo.SysSafeLogVo;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysSafeLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

import java.util.Map;

/**
 * web的调用时间统计拦截器
 * dev环境有效
 *
 * @author Lion Li
 * @since 3.3.0
 */
@Slf4j
public class PlusWebInvokeTimeInterceptor implements HandlerInterceptor {

    private final String prodProfile = "prod";

    private final TransmittableThreadLocal<StopWatch> invokeTimeTL = new TransmittableThreadLocal<>();
    private final ISysMenuService menuService;
    private final ISysSafeLogService sysSafeLogService;

    public PlusWebInvokeTimeInterceptor(ISysMenuService menuService, ISysSafeLogService sysSafeLogService) {
        this.menuService = menuService;
        this.sysSafeLogService = sysSafeLogService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SafeLoginService bean = BeanHelper.getBean(SafeLoginService.class);
        if(!bean.checkLongTimeUnOperate(request)){
            throw new FailException(505,"系统超时未操作，请重新登录");
        }
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            //长时间未操作退出

            String url = request.getMethod() + " " + request.getRequestURI();
            // 打印请求参数
            if (isJsonRequest(request)) {
                String jsonParam = "";
                if (request instanceof RepeatedlyRequestWrapper) {
                    BufferedReader reader = request.getReader();
                    jsonParam = IoUtil.read(reader);
                }
                log.debug("[PLUS]开始请求 => URL[{}],参数类型[json],参数:[{}]", url, jsonParam);
            } else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (MapUtil.isNotEmpty(parameterMap)) {
                    String parameters = JsonUtils.toJsonString(parameterMap);
                    log.debug("[PLUS]开始请求 => URL[{}],参数类型[param],参数:[{}]", url, parameters);
                } else {
                    log.debug("[PLUS]开始请求 => URL[{}],无参数", url);
                }
            }

            StopWatch stopWatch = new StopWatch();
            invokeTimeTL.set(stopWatch);
            stopWatch.start();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            StopWatch stopWatch = invokeTimeTL.get();
            stopWatch.stop();
            log.debug("[PLUS]结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + request.getRequestURI(), stopWatch.getTime());
            invokeTimeTL.remove();
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

//    public void checkMethodPermission(Method method, HttpServletRequest request){
//      //  Map<String,Object> paramMap = RequestUtils.getMapByRequestJson(request);
//        // 进行验证
//        SaCheckPermission permission = AnnotationUtils.findAnnotation(method, SaCheckPermission.class);
//        if (permission!= null && permission.value().length > 0) {
//            for (String p : permission.value()) {
//                SysMenuDTO sysMenu =  menuService.checkMenuByOperatorType(p);
//              if (sysMenu != null) {
//                  log.info("权限校验通过",sysMenu);
//                  //加入安全日志
//                  sysSafeLogService.addSafeLoginEvent("",sysMenu.getMenuName(),request.getMethod(),"",sysMenu.getOperator(),"");
//              }
//            }
//        }
//    }

}
