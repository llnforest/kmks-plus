package com.kmks.jianguan.service.impl;

import cn.hutool.json.JSONObject;
import com.kmks.jianguan.domain.bo.*;
import com.kmks.jianguan.domain.vo.*;
import com.kmks.jianguan.handler.HttpHandler;
import com.kmks.jianguan.params.FilesItemParam;
import com.kmks.jianguan.response.CommonResult;
import com.kmks.jianguan.response.Result;
import com.kmks.jianguan.service.IJgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/7 10:29
 */
@Service
public class JgServiceImpl implements IJgService {

    @Autowired
    private HttpHandler httpHandler;

    /**
     * 02-21-000001-人证比对
     *
     * @param bo bo
     * @return {@link Result}<{@link A0221000001Vo}>
     */
    @Override
    public A0221000001Vo a0221000001(A0221000001Bo bo, String zxzp, String xczp) {
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("zxzp",zxzp));
        files.add(new FilesItemParam("xczp",xczp));
        CommonBo<A0221000001Bo> commonBo = httpHandler.getCommonBo(bo, files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000001Vo) httpHandler.getVo(result,A0221000001Vo.class);
    }


    /**
     * 02-21-000002-约考信息查询（17C08）
     *
     * @param bo 薄
     * @return {@link A0221000002Vo}
     */
    @Override
    public A0221000002Vo a0221000002(A0221000002Bo bo){
        CommonBo<A0221000002Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000002Vo) httpHandler.getVo(result,A0221000002Vo.class);
    }

    /**
     * 02-21-000003-考试状态查询（17C10）
     *
     * @param bo 薄
     * @return {@link A0221000003Vo}
     */
    @Override
    public A0221000003Vo a0221000003(A0221000003Bo bo){
        CommonBo<A0221000003Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000003Vo) httpHandler.getVo(result,A0221000003Vo.class);
    }

    /**
     * 02-21-000004-考生签到（09C56）
     *
     * @param bo   请求参数param部分
     * @param zxzp zxzp
     * @return {@link A0221000004Vo}
     */
    @Override
    public A0221000004Vo a0221000004(A0221000004Bo bo, String zxzp){
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("",zxzp));
        CommonBo<A0221000004Bo> commonBo = httpHandler.getCommonBo(bo, files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000004Vo) httpHandler.getVo(result,A0221000004Vo.class);
    }

    /**
     * 02-21-000005-随机分配考台（09C55）
     *
     * @param bo 薄
     * @return {@link A0221000005Vo}
     */
    @Override
    public A0221000005Vo a0221000005(A0221000005Bo bo){
        CommonBo<A0221000005Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000005Vo) httpHandler.getVo(result,A0221000005Vo.class);
    }

    /**
     * 02-21-000006-考试系统与监管系统时间同步（17C09）
     *
     * @param bo 薄
     * @return {@link A0221000006Vo}
     */
    @Override
    public A0221000006Vo a0221000006(A0221000006Bo bo){
        CommonBo<A0221000006Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000006Vo) httpHandler.getVo(result,A0221000006Vo.class);
    }

    /**
     * 02-21-000007-考试签到（17CB2）
     *
     * @param bo   请求参数param部分
     * @param zxzp zxzp
     * @return {@link A0221000007Vo}
     */
    @Override
    public A0221000007Vo a0221000007(A0221000007Bo bo,String zxzp){
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("",zxzp));
        CommonBo<A0221000007Bo> commonBo = httpHandler.getCommonBo(bo, files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000007Vo) httpHandler.getVo(result,A0221000007Vo.class);
    }

    /**
     * 02-21-000008-随机安排考生（17CB3）
     *
     * @param bo 薄
     * @return {@link A0221000008Vo}
     */
    @Override
    public A0221000008Vo a0221000008(A0221000008Bo bo){
        CommonBo<A0221000008Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000008Vo) httpHandler.getVo(result,A0221000008Vo.class);
    }

    /**
     * 02-21-000009-考试开始（17C51）
     *
     * @param bo   请求参数param部分
     * @param kszp 考试照片
     * @return {@link A0221000009Vo}
     */
    @Override
    public A0221000009Vo a0221000009(A0221000009Bo bo,String kszp){
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("",kszp));
        CommonBo<A0221000009Bo> commonBo = httpHandler.getCommonBo(bo, files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000009Vo) httpHandler.getVo(result,A0221000009Vo.class);
    }

    /**
     * 02-21-000010-考试项目开始（17C52）
     *
     * @param bo 薄
     * @return {@link A0221000010Vo}
     */
    @Override
    public A0221000010Vo a0221000010(A0221000010Bo bo){
        CommonBo<A0221000010Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000010Vo) httpHandler.getVo(result,A0221000010Vo.class);
    }

    /**
     * 02-21-000011-考试扣分（17C53）
     *
     * @param bo 薄
     * @return {@link A0221000011Vo}
     */
    @Override
    public A0221000011Vo a0221000011(A0221000011Bo bo){
        CommonBo<A0221000011Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000011Vo) httpHandler.getVo(result,A0221000011Vo.class);
    }

    /**
     * 02-21-000012-考试过程图片（17C54）
     *
     * @param bo      请求参数param部分
     * @param kscgczp kscgczp
     * @return {@link A0221000012Vo}
     */
    @Override
    public A0221000012Vo a0221000012(A0221000012Bo bo,String kscgczp){
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("",kscgczp));
        CommonBo<A0221000012Bo> commonBo = httpHandler.getCommonBo(bo,files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000012Vo) httpHandler.getVo(result,A0221000012Vo.class);
    }

    /**
     * 02-21-000013-考试项目结束（17C55）
     *
     * @param bo 请求参数param部分
     * @return {@link A0221000013Vo}
     */
    @Override
    public A0221000013Vo a0221000013(A0221000013Bo bo){
        CommonBo<A0221000013Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000013Vo) httpHandler.getVo(result,A0221000013Vo.class);
    }

    /**
     * 02-21-000014-考试科目考试结束（17C56）
     *
     * @param bo   请求参数param部分
     * @param kszp Kszp
     * @return {@link A0221000014Vo}
     */
    @Override
    public A0221000014Vo a0221000014(A0221000014Bo bo,String kszp){
        List<FilesItemParam> files = new ArrayList<>();
        files.add(new FilesItemParam("",kszp));
        CommonBo<A0221000014Bo> commonBo = httpHandler.getCommonBo(bo,files);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000014Vo) httpHandler.getVo(result,A0221000014Vo.class);
    }

    /**
     * 02-21-000015-写入考试轨迹（17CC3）
     *
     * @param bo 薄
     * @return {@link A0221000015Vo}
     */
    @Override
    public A0221000015Vo a0221000015(A0221000015Bo bo){
        CommonBo<A0221000015Bo> commonBo = httpHandler.getCommonBo(bo);
        CommonResult<Result<JSONObject>> result = httpHandler.sendData(commonBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A0221000015Vo) httpHandler.getVo(result,A0221000015Vo.class);
    }
}
