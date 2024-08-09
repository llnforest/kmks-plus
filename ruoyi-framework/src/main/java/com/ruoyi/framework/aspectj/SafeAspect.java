package com.ruoyi.framework.aspectj;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.service.DictService;
import com.ruoyi.common.enums.HttpMethod;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.redisData.RedisMenuOperatorData;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.ISysSafeLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 *
 * 安全日志处理
 */
@Slf4j
@Aspect
@Component
public class SafeAspect {

    @Resource
    private ISysSafeLogService safeLogService;

    @Resource
    private DictService dictService;

    private SaCheckPermission saCheckPermission;

    @Before("@annotation(saCheckPermission)")
    public void doBefore(JoinPoint joinPoint,  SaCheckPermission saCheckPermission) throws Throwable {
        log.info("sa:{}",saCheckPermission.value());
        List<String> menuCoreList = RedisMenuOperatorData.getMenuCoreReturn();
        List<String> menuUncommonList = RedisMenuOperatorData.getMenuUncommonReturn();
        List<String> menuOperatorList = RedisMenuOperatorData.getMenuOperatorReturn();

        // 获取当前方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters= method.getParameters();
        String params = LogHandleUtils.argsArrayToString(joinPoint.getArgs());

        // 获取方法上的 logAnnotation 注解
        Log logAnnotation = method.getAnnotation(Log.class);
        String title = "";
        String operate = "";
        StringBuilder remarkBuilder = new StringBuilder();
        if (logAnnotation != null) {
            // 获取 logAnnotation 注解的信息
            title = logAnnotation.title();
            operate = dictService.getDictLabel("sys_oper_type",String.valueOf(logAnnotation.businessType().ordinal()));

            //根据反射及注解完善参数说明
            if(ServletUtils.getRequest().getMethod().equals(HttpMethod.DELETE.name())){
                remarkBuilder.append("删除记录【"+params+"】,");
            }else if(parameters.length > 0){
                //获取字段
                if(parameters[0].getType() == Long[].class || parameters[0].getType() == Long.class|| parameters[0].getType() == String.class){
                    remarkBuilder.append("操作记录【"+params+"】,");
                }else{
                    Field[] fields = parameters[0].getType().getDeclaredFields();
                    Map<String,Object> dataMap = JsonUtils.parseMap(params);
                    if(dataMap != null) {
                        for (Field field : fields) {
                            if (ObjectUtil.isNull(dataMap.get(field.getName()))) continue;
                            //获取注解
                            Column column = field.getDeclaredAnnotation(Column.class);
                            if (column == null) continue;
                            //字典值处理
                            if (StringUtils.isNotBlank(column.dict())) {
                                remarkBuilder.append(column.value() + ":【" + SpringUtils.getBean(DictService.class).getDictLabel(column.dict(), String.valueOf(dataMap.get(field.getName())), column.separator()) + "】,");
                            } else {
                                remarkBuilder.append(column.value() + ":【" + dataMap.get(field.getName()) + "】,");

                            }
                        }
                    }
                }
            }

            if(remarkBuilder.length() > 0) {
                if(StringUtils.isNotBlank(logAnnotation.remark())){
                    remarkBuilder.append(logAnnotation.remark());
                }else{
                    remarkBuilder.deleteCharAt(remarkBuilder.length()-1);
                }

            }else{
                remarkBuilder.append(logAnnotation.remark());
            }
        }

        for (String per : saCheckPermission.value()) {
            if(menuCoreList.contains(per)){
                //加入安全日志
                safeLogService.addSafeLoginEvent(LoginHelper.getLoginUser().getUsername(),title,operate,"成功","核心业务",StringUtils.substring(remarkBuilder.toString(),0,2000));
            }

            if(menuUncommonList.contains(per)){
                //加入安全日志
                safeLogService.addSafeLoginEvent(LoginHelper.getLoginUser().getUsername(),title,operate,"成功","非常规业务",StringUtils.substring(remarkBuilder.toString(),0,2000));
            }

            if(menuOperatorList.contains(per) && LoginHelper.getLoginUser().getOperator().equals("N")){
                throw new FailException("警员操作功能非警员身份禁止访问");
            }

        }

    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(saCheckPermission)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint,  SaCheckPermission saCheckPermission, Object jsonResult) {
        this.saCheckPermission = saCheckPermission;
    }




}
