package com.kmks.w2.service;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kmks.w2.domain.bo.W2ConfigBo;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.redis.CacheUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.system.service.ISysSafeLogService;
import com.ruoyi.system.service.ISysUserBlackService;
import com.ruoyi.system.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;

/**
 * @author Star
 * @description: 安全检测
 * @date 2023/3/1 14:04
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SafeLoginService {
    private final IW2ConfigService iw2ConfigService;
    private final ISysSafeLogService safeLogService;
    private final SysLoginService sysLoginService;
    private final ISysUserBlackService blackService;

    /**
     * @return boolean
     * @Description 登录之前
     * @Param []
     * @Author lynn 14:08 2023/3/1
     **/
    public void beforeLogin(Map<String,Object> parameterMap){
        log.info("beforeLogin");
        log.info("密码：{}",parameterMap.get("password"));

        //判断是否只在一个终端登录
        if(RedisUtils.isExistsObject(CacheConstants.ONLINE_SINGLE_KEY + parameterMap.get("username"))) {
            throw new UserException("login.lock.other");
        }

        //判断同时在线会话数
        if(RedisUtils.keys(CacheConstants.ONLINE_SINGLE_KEY+"*").size() > Integer.valueOf(getSafeConfig("29"))){
            throw new UserException("login.lock.max", getSafeConfig("29"));
        }

        //1、判断是否在黑名单中
        if(ObjectUtil.isNotNull(RedisUtils.getCacheObject(CacheConstants.USER_BLACK_KEY + ServletUtils.getClientIP()))){
            throw new UserException("login.lock.ip", ServletUtils.getClientIP());
        }
        if(ObjectUtil.isNotNull(RedisUtils.getCacheObject(CacheConstants.USER_BLACK_KEY + parameterMap.get("username")))){
            throw new UserException("login.lock.username",parameterMap.get("username"));
        }

        //2、判断用户高频登录
        String limitKey = CacheConstants.DAY_LOGIN_LIMIT_KEY + DateUtils.getDate()+parameterMap.get("username");
        long dayTimes = RedisUtils.getAtomicValue(limitKey);
        if(ObjectUtil.isNull(dayTimes)){
            dayTimes = 0;
            RedisUtils.setAtomicValue(limitKey,dayTimes,Duration.ofDays(1));
        }
        if(dayTimes >= Long.valueOf(getSafeConfig("26"))){
            throw new UserException("login.lock.rate.username",parameterMap.get("username"));
        }
        RedisUtils.incrAtomicValue(limitKey);

        //3、验证用户登录时间段
        //登录时间段验证
        String timeRange = getSafeConfig("12") + "-" + getSafeConfig("13");
        if(!CompareTimeUtils.timeIsInRound(DateUtils.dateTimeNow("HH:mm"),timeRange,"HH:mm")){
            throw new UserException("user.invalid.time.range");
        }

        //4、账户长期未使用
        String lastLoginKey = CacheConstants.LAST_LOGIN_KEY + parameterMap.get("username");
        if(RedisUtils.isExistsObject(lastLoginKey)){
            long days = (long)(System.currentTimeMillis() - (long)RedisUtils.getCacheObject(lastLoginKey))/24/60/60/1000;
            if(days > Integer.valueOf(getSafeConfig("27"))){
                throw new UserException("login.lock.last.time",getSafeConfig("27"));
            }
        }

    }

    /**
     * @Description 登录结束异常
     * @Param [ex]
     * @return void
     * @Author lynn 13:05 2023/3/2
    **/
    public void afterLogin(String username,Exception ex){
        //加入安全日志

        //加入登录日志
        sysLoginService.recordLogininfor(username, Constants.LOGIN_FAIL, ex.getMessage());

        //获取驾考参数配置
        W2ConfigBo w2ConfigBo = new W2ConfigBo();
        w2ConfigBo.setIFlag(2L);

        //只增加ip 错误次数
        checkErrorTimes(ServletUtils.getClientIP(),Integer.valueOf(getSafeConfig("10")),"2");
        if(ex instanceof UserException){
            //增加ip 错误次数  以及 账户次数
            checkErrorTimes(username,Integer.valueOf(getSafeConfig("11")),"1");
        }
    }

    /**
     * 失败次数校验
     * type:1客户端  2用户
     */
    private void checkErrorTimes(String key,int maxRetryCount,String blackType) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + key;
        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = RedisUtils.getCacheObject(errorKey);

        // 是否第一次
        errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
        // 达到规定错误次数 则锁定登录
        RedisUtils.setCacheObject(errorKey, errorNumber);
        if (errorNumber.equals(maxRetryCount)) {
            RedisUtils.setCacheObject(CacheConstants.USER_BLACK_KEY + key, 1);
            String msg = blackType.equals("1") ? "账户" : "终端";
            //加入安全日志
            safeLogService.addSafeLoginEvent(key,"用户登录","检测","失败","正常业务",msg+MessageUtils.message("login.lock.times",key,maxRetryCount));
            //加入黑名单
            blackService.addBlackEvent(key,blackType,msg+MessageUtils.message("login.lock.times",key,maxRetryCount));
        }
    }

    /**
     * @Description 清空错误次数
     * @Param [errorKeyName]
     * @return void
     * @Author lynn 18:38 2023/3/2
    **/
    public void clearErrorTimes(String username){
        // 登录成功 清空错误次数
        RedisUtils.deleteObject(CacheConstants.PWD_ERR_CNT_KEY + username);
        RedisUtils.deleteObject(CacheConstants.PWD_ERR_CNT_KEY + ServletUtils.getClientIP());
    }
    
    public void addSafeLog(){
        log.info("添加安全日志");
    }

    /**
     * @Description 获取安全配置
     * @Param []
     * @return java.util.Map<java.lang.Long,java.lang.String>
     * @Author lynn 9:22 2023/3/6
    **/
    public Map<String,String> getSafeConfigMap(){
        String key = CacheConstants.LOGIN_SAFE_CONFIG_KEY;
        Map<String,String> map = new HashMap<>();
        if(RedisUtils.isExistsObject(key)){
            map = RedisUtils.getCacheMap(key);
        }else{
            //获取驾考参数配置
            W2ConfigBo w2ConfigBo = new W2ConfigBo();
            w2ConfigBo.setIFlag(2L);
            map = iw2ConfigService.getCodeValueMap(w2ConfigBo);
            RedisUtils.setCacheMap(key,map,Duration.ofMinutes(1));
        }
        return map;
    }

    /**
     * @Description 获取对应的安全参数
     * @Param [code]
     * @return java.lang.String
     * @Author lynn 9:25 2023/3/6
    **/
    public String getSafeConfig(String code){
        Map<String,String> configMap = getSafeConfigMap();
        return configMap.get(code);
    }

    public Boolean checkLongTimeUnOperate(HttpServletRequest request){
        String auth = StringUtils.trimToEmpty(request.getHeader(SaManager.getConfig().getTokenName()));
        if(StringUtils.isNotBlank(auth)){
            if(CacheUtils.get(CacheConstants.ACCOUNT_OPERATE_TIME,auth) != null && (long)CacheUtils.get(CacheConstants.ACCOUNT_OPERATE_TIME,auth) - DateUtil.current() < Long.valueOf(getSafeConfig("1"))*60*1000){
                CacheUtils.put(CacheConstants.ACCOUNT_OPERATE_TIME,auth, DateUtil.current());
                return true;
            }else{
                StpUtil.logout();
                return false;
            }

        }
        return true;
    }
}