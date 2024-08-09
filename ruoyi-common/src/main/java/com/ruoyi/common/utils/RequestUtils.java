package com.ruoyi.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.filter.RepeatedlyRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Map;

/**
 * @author Star
 * @description: TODO
 * @date 2023/3/2 18:51
 */
public class RequestUtils {
    public static Map<String,Object> getMapByRequestJson(HttpServletRequest request){
        String jsonParam = "";
        try{
            if (request instanceof RepeatedlyRequestWrapper) {
                BufferedReader reader = request.getReader();
                jsonParam = IoUtil.read(reader);
            }
        }catch (Exception ex){

        }
        return JSONUtil.parseObj(jsonParam);
    }
}
