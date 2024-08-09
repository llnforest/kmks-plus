package com.kmks.jianguan.handler;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.kmks.jianguan.domain.bo.CdxxBo;
import com.kmks.jianguan.domain.bo.CommonBo;
import com.kmks.jianguan.domain.vo.A0221000001Vo;
import com.kmks.jianguan.enums.CdxxEnum;
import com.kmks.jianguan.enums.HttpEnum;
import com.kmks.jianguan.params.FilesItemParam;
import com.kmks.jianguan.response.CommonResult;
import com.kmks.jianguan.response.Response;
import com.kmks.jianguan.response.Result;
import com.kmks.jianguan.utils.HttpUtils;
import com.ruoyi.common.exception.api.FailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * http处理程序
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/11 10:33
 */
@Component
@Slf4j
public class HttpHandler<T,C> {


    @Autowired
    private HttpUtils httpUtils;

    public <T> CommonBo<T> getCommonBo(T t, List<FilesItemParam> files){
        CommonBo<T> commonBo = getCommonBo(t);
        commonBo.setFiles(files);
        return commonBo;
    }

    public <T> CommonBo<T> getCommonBo(T t){
        CommonBo<T> commonBo = new CommonBo<>();
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(t.getClass());
        CdxxBo cdxxBo = new CdxxBo();
        cdxxBo.setDxbj(cdxxEnum.getCzlx());
        cdxxBo.setWgcdbh(cdxxEnum.getSjbs());
        commonBo.setCdxx(cdxxBo);
        commonBo.setData(t);
        return commonBo;
    }

    public <V> V getVo(CommonResult<Result<JSONObject>> commonResult,Class<V> v){
        return commonResult.getData().get(0).getResult().toBean(v);
    }

    /**
     * 发送数据
     *
     * @param t t
     */
    public <T> CommonResult<Result<JSONObject>> sendData(CommonBo<T> t){
        //发送数据
        String s = httpUtils.httpPost(t);
        // 使用 TypeReference 创建类型引用
        TypeReference<Response<CommonResult<Result<JSONObject>>>> typeRef = new TypeReference<Response<CommonResult<Result<JSONObject>>>>() {};
        Response<CommonResult<Result<JSONObject>>> response = JSONUtil.toBean(s, typeRef,true);
        log.info("response:{}",response);
        if(response.getCode() != HttpEnum.SUCCESS.getCode()){
            throw new FailException(response.getCode(),response.getMessage());
        }
        if(!response.getData().getFhjg().equals("1")){
            throw new FailException("接口异常："+response.getData().getCwdm()+":"+response.getData().getFhxxms());
        }

        return response.getData();
    }


}
