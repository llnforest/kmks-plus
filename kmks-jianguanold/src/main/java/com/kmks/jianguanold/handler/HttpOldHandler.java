package com.kmks.jianguanold.handler;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kmks.jianguanold.domain.bo.CdxxOldBo;
import com.kmks.jianguanold.domain.bo.CommonOldBo;
import com.kmks.jianguanold.enums.CdxxEnum;
import com.kmks.jianguanold.enums.HttpEnum;
import com.kmks.jianguanold.response.Response;
import com.kmks.jianguanold.utils.HttpOldUtils;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * http处理程序
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/11 10:33
 */
@Component
@Slf4j
public class HttpOldHandler<T,C> {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private HttpOldUtils httpOldUtils;


    public <T> CommonOldBo<T> getCommonBo(T t, CdxxEnum cdxxEnum){
        CommonOldBo<T> commonOldBo = new CommonOldBo<>();
        CdxxOldBo cdxxOldBo = new CdxxOldBo();
        cdxxOldBo.setXtlb(configService.selectConfigByKey(CacheNames.JG_OLD_XTLB));
        cdxxOldBo.setJkxlh(configService.selectConfigByKey(CacheNames.JG_COMMON_JKXLH));
        cdxxOldBo.setJkid(cdxxEnum.getJkid());
        commonOldBo.setCdxx(cdxxOldBo);
        commonOldBo.setData(t);
        return commonOldBo;
    }

    public <V> V getVo(JSONObject jsonObject,Class<V> v){
        return jsonObject.toBean(v);
    }

    /**
     * 发送数据
     *
     * @param t t
     */
    public <T> JSONObject sendData(CommonOldBo<T> t){
        //发送数据
        String s = httpOldUtils.httpPost(t);
        // 使用 TypeReference 创建类型引用
        TypeReference<Response<JSONObject>> typeRef = new TypeReference<Response<JSONObject>>() {};
        Response<JSONObject> response = JSONUtil.toBean(s, typeRef,true);
        log.info("response:{}",response);
        if(response.getCode() != HttpEnum.SUCCESS.getCode()){
            throw new FailException(response.getCode(),response.getMessage());
        }

        return response.getData();
    }


}
