package com.ruoyi.common.redisData;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.utils.redis.RedisUtils;

import java.util.Map;

/**
 * @author Star
 * @description: 路线配置数据
 * @date 2023/7/14 15:06
 */
public class RedisLineConfigData {

    /**
     * 获得路线配置数据返回
     *
     * @return {@link Map}<{@link K},{@link V}>
     **/
    public static <K, V> Map<K, V> getLineConfigReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_LINE_CONFIG_KEY);
    }

    /**
     * 路线配置数据集
     *
     * @param dataMap 数据列表
     * @return {@link Boolean}
     */
    public static <T> Boolean setLineConfigData(T dataMap) {
        RedisUtils.setCacheObject(CacheConstants.W2_LINE_CONFIG_KEY, dataMap);
        return true;
    }

    /**
     * 删除路线配置数据
     *
     * @return {@link Boolean}
     */
    public static Boolean deleteLineConfigData() {
        return RedisUtils.deleteObject(CacheConstants.W2_LINE_CONFIG_KEY);
    }

}
