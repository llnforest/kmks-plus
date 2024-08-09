package com.kmks.w2.utils;

import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.redis.RedisUtils;

import java.time.Duration;

/**
 * 通用缓存配置
 * @author Star
 * @description: TODO
 * @date 2024/5/31 14:47
 */
public class RedisUtil {
    /**
     * 设置最大bdxh
     *
     * @param bdxh bdxh
     */
    public static void setMaxBdxh(Long bdxh){
        RedisUtils.setCacheObject(CacheNames.MAX_BDXH_KEY,bdxh == null?0l:bdxh, Duration.ofDays(2));
    }


    /**
     * 获取最大bdxh
     *
     * @return {@link Long}
     */
    public static Long getMaxBdxh(){
        return (Long) RedisUtils.getCacheObject(CacheNames.MAX_BDXH_KEY);
    }


    /**
     * 递增bdxh
     *
     * @return {@link Long}
     */
    public static Long incrBdxh(){
        setMaxBdxh(getMaxBdxh() + 1);
        return getMaxBdxh();
    }
}
