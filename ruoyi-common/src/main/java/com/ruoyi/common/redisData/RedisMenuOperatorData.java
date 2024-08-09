package com.ruoyi.common.redisData;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.utils.redis.RedisUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Star
 * @description: 菜单操作类型数据
 * @date 2023/7/14 15:06
 */
public class RedisMenuOperatorData {

    /**
     * 获得菜单核心功能数据返回
     *
     * * @return {@link List}<{@link String}>
     */
    public static List<String> getMenuCoreReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_MENU_CORE_KEY);
    }

    /**
     * 获得菜单非常规功能数据返回
     *
     * * @return {@link List}<{@link String}>
     */
    public static List<String> getMenuUncommonReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_MENU_UNCOMMON_KEY);
    }

    /**
     * 获得菜单操作类型数据返回
     *
     * * @return {@link List}<{@link String}>
     */
    public static List<String> getMenuOperatorReturn() {
        return RedisUtils.getCacheObject(CacheConstants.W2_MENU_OPERATOR_KEY);
    }

    /**
     * 设置菜单操作类型数据
     *
     * @param coreList     核心清单
     * @param uncommonList 不常见列表
     * @param operatorList 操作员列表
     * @return {@link Boolean}
     */
    public static <T> Boolean setMenuOperatorData(T coreList,T uncommonList,T operatorList) {
        RedisUtils.setCacheObject(CacheConstants.W2_MENU_CORE_KEY, coreList);
        RedisUtils.setCacheObject(CacheConstants.W2_MENU_UNCOMMON_KEY, uncommonList);
        RedisUtils.setCacheObject(CacheConstants.W2_MENU_OPERATOR_KEY, operatorList);
        return true;
    }

    /**
     * 删除菜单操作类型数据
     *
     * @return {@link Boolean}
     */
    public static Boolean deleteMenuOperatorData() {
        RedisUtils.deleteObject(CacheConstants.W2_MENU_CORE_KEY);
        RedisUtils.deleteObject(CacheConstants.W2_MENU_UNCOMMON_KEY);
        RedisUtils.deleteObject(CacheConstants.W2_MENU_OPERATOR_KEY);
        return true;
    }

}
