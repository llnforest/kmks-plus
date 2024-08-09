package com.ruoyi.common.redisData;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.utils.redis.RedisUtils;

import java.util.Map;

/**
 * @author Star
 * @description: 扣分代码数据
 * @date 2023/7/14 15:06
 */
public class RedisKfConfigData {

    /**
     * 获得扣分代码数据返回
     *
     * @return {@link Map}<{@link K},{@link V}>
     **/
    public static <K, V> Map<K, V> getKfConfigReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_KF_CONFIG_KEY);
    }

    /**
     * 扣分代码数据集
     *
     * @param dataMap 数据列表
     * @return {@link Boolean}
     */
    public static <T> Boolean setKfConfigData(T dataMap) {
        RedisUtils.setCacheObject(CacheConstants.W2_KF_CONFIG_KEY, dataMap);
        return true;
    }

    /**
     * 删除扣分代码数据
     *
     * @return {@link Boolean}
     */
    public static Boolean deleteKfConfigData() {
        return RedisUtils.deleteObject(CacheConstants.W2_KF_CONFIG_KEY);
    }

}
