package com.kmks.jianguan.utils;

import cn.hutool.json.JSONUtil;
import com.kmks.jianguan.domain.bo.CommonBo;
import com.ruoyi.common.utils.LogUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Star
 * @description: TODO
 * @date 2023/5/13 23:22
 */
@Component
public class HttpUtils {

    @Value("${jg.url}")
    private String url;

    /**
     * http post
     *
     * @param t t
     * @return {@link String}
     */
    public <T> String httpPost(CommonBo<T> t){
        String bodyString = JSONUtil.toJsonStr(t);
        LogUtils.server(">>>{}",bodyString);
        String body = cn.hutool.http.HttpUtil.createPost(url)
                .contentType("application/json")
                .body(bodyString)
                .execute().body();
        LogUtils.server("<<<{}",body);
        return body;
    }
}
