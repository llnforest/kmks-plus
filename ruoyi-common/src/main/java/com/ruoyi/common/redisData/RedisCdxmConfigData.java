package com.ruoyi.common.redisData;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.utils.redis.RedisUtils;

import java.util.Map;

/**
 * @author Star
 * @description: 场地项目数据
 * @date 2023/7/14 15:06
 */
public class RedisCdxmConfigData {

    /**
     * 获得场地项目数据返回
     *
     * @return {@link Map}<{@link K},{@link V}>
     **/
    public static <K, V> Map<K, V> getCdxmConfigReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_CDXM_CONFIG_KEY);
    }

    /**
     * 场地项目数据集
     *
     * @param dataMap 数据列表
     * @return {@link Boolean}
     */
    public static <T> Boolean setCdxmConfigData(T dataMap) {
        RedisUtils.setCacheObject(CacheConstants.W2_CDXM_CONFIG_KEY, dataMap);
        return true;
    }

    /**
     * 删除场地项目数据
     *
     * @return {@link Boolean}
     */
    public static Boolean deleteCdxmConfigData() {
        return RedisUtils.deleteObject(CacheConstants.W2_CDXM_CONFIG_KEY);
    }

}
