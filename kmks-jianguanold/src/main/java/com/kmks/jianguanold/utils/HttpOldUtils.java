package com.kmks.jianguanold.utils;

import cn.hutool.json.JSONUtil;
import com.kmks.jianguanold.domain.bo.CommonOldBo;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.LogUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author Star
 * @description: TODO
 * @date 2023/5/13 23:22
 */
@Component
public class HttpOldUtils {

    @Resource
    private ISysConfigService configService;

    /**
     * http post
     *
     * @param t t
     * @return {@link String}
     */
    public <T> String httpPost(CommonOldBo<T> t){
        String bodyString = JSONUtil.toJsonStr(t);
        LogUtils.server(">>>{}",bodyString);
        String body = cn.hutool.http.HttpUtil.createPost(configService.selectConfigByKey(CacheNames.SYS_JK_JG_API))
                .contentType("application/json")
                .body(bodyString)
                .execute().body();
        LogUtils.server("<<<{}",body);
        return body;
    }
}
