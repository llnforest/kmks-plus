package com.kmks.w2.interceptor;

import com.kmks.w2.service.SafeLoginService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.RequestUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.ISysSafeLogService;
import com.ruoyi.system.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Star
 * @description: 登录拦截器
 * @date 2023/3/1 10:50
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final String prodProfile = "dev";
    private final SafeLoginService safeLoginService;
    private final SysLoginService sysLoginService;
    private final ISysSafeLogService safeLogService;

    /**
     * @Description 目标方法执行之前
     * @Param [request, response, handler]
     * @return boolean
     * @Author lynn 10:54 2023/3/1
    **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        if (prodProfile.equals(SpringUtils.getActiveProfile())) {
            Map<String,Object> paramMap = RequestUtils.getMapByRequestJson(request);
            try{
                safeLoginService.beforeLogin(paramMap);
            }catch (Exception ex){
                sysLoginService.recordLogininfor(String.valueOf(paramMap.get("username")), Constants.LOGIN_FAIL, ex.getMessage());
                //加入安全日志
                safeLogService.addSafeLoginEvent(String.valueOf(paramMap.get("username")),"用户登录","检测","失败","正常业务",ex.getMessage());
                throw ex;
            }
//            输出流返回
//            if (R.isError(result)) {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/json; charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.
//                out.append(JSONUtil.toJsonStr(result));
//                return false;
//            }
        }
        return true;
        // 获取经过拦截器的路径
//        String requestURI = request.getRequestURI();
//        log.info("进入登录1");
//        return true;
        //登录检查逻辑
//        HttpSession session = request.getSession();
//        Object loginUser = session.getAttribute("loginUser");
//        if(loginUser !=null){
//            //放行
//            return true;
//        }
//        //拦截就是未登录,自动跳转到登录页面，然后写拦截住的逻辑
//        return false;
    }

    /**
     * @Description 目标方法执行之后
     * @Param [request, response, handler, modelAndView]
     * @return void
     * @Author lynn 10:56 2023/3/1
    **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        log.info("请求结束");
    }

    /**
     * @Description 目标方法执行之后
     * @Param [request, response, handler, modelAndView]
     * @return void
     * @Author lynn 10:56 2023/3/1
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception{
        String jsonParam = "";
//        if (request instanceof RepeatedlyRequestWrapper) {
//            BufferedReader reader = request.getReader();
//            jsonParam = IoUtil.read(reader);
//        }
//        response.getOutputStream().toByteArray();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            response.getOutputStream().write(outputStream);
//            log.info("返回结果；{}",outputStream.toString(StandardCharsets.UTF_8.name()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        if(exception != null){
//            log.info(exception.getMessage()+"");
//        }
        log.info("结束登录");
    }



}
