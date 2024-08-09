package com.ruoyi.system.utils;

import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;

/**
 * @author Star
 * @description: TODO
 * @date 2024/6/18 11:34
 */
public class ConfigUtil {
    public static Boolean getValid(){
        SysConfigServiceImpl bean = BeanHelper.getBean(SysConfigServiceImpl.class);
        return bean.selectConfigByKey(CacheNames.VALID_KEY).equals("1");
    }
}
