package com.kmks.jianguanold.service.impl;

import cn.hutool.json.JSONObject;
import com.kmks.jianguanold.domain.bo.*;
import com.kmks.jianguanold.domain.vo.*;
import com.kmks.jianguanold.enums.*;
import com.kmks.jianguanold.handler.HttpOldHandler;
import com.kmks.jianguanold.service.IJgOldService;
import com.ruoyi.common.exception.api.FailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/7 10:29
 */
@Service
public class JgOldServiceImpl implements IJgOldService {

    @Autowired
    private HttpOldHandler httpOldHandler;

    /**
     * 考试场地备案信息下载
     *
     * @param bo bo
     * @return {@link A17C01Vo}
     */
    @Override
    public A17C01Vo a17c01(A17C01Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C01Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C01Vo vo = (A17C01Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        if (vo.getHead().getRownum() <= 0) {
            throw new FailException("同步下载场地备案信息记录数为0");
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试设备备案信息下载
     *
     * @param bo bo
     * @return {@link A17C02Vo}
     */
    @Override
    public A17C02Vo a17c02(A17C02Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C02Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C02Vo vo = (A17C02Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        if (vo.getHead().getRownum() <= 0) {
            throw new FailException("同步下载设备备案信息记录数为0");
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试员备案信息同步
     *
     * @param bo bo
     * @return {@link A17C04Vo}
     */
    @Override
    public A17C04Vo a17c04(A17C04Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C04Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        A17C04Vo vo = (A17C04Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        // 根据监管接口处理业务逻辑
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        if (vo.getHead().getRownum() <= 0) {
            throw new FailException("同步下载考试员备案信息记录数为0");
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 驾校备案信息下载
     *
     * @param bo bo
     * @return {@link A17C05Vo}
     */
    @Override
    public A17C05Vo a17c05(A17C05Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C05Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        A17C05Vo vo = (A17C05Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        // 根据监管接口处理业务逻辑
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        if (vo.getHead().getRownum() <= 0) {
            throw new FailException("同步下载驾校备案信息记录数为0");
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试计划分组信息下载
     *
     * @param bo bo
     * @return {@link A17C06Vo}
     */
    @Override
    public A17C06Vo a17c06(A17C06Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C06Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C06Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 考试分组明细信息下载
     *
     * @param bo bo
     * @return {@link A17C07Vo}
     */
    @Override
    public A17C07Vo a17c07(A17C07Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C07Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C07Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 考试预约信息下载
     *
     * @param bo bo
     * @return {@link A17C08Vo}
     */
    @Override
    public A17C08Vo a17c08(A17C08Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C08Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        A17C08Vo vo = (A17C08Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        // 根据监管接口处理业务逻辑
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        if (vo.getHead().getRownum() <= 0) {
            throw new FailException("未查询到符合条件的预约信息");
        }
        // 返回监管底层数据
        return (A17C08Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 时间同步
     *
     * @param bo bo
     * @return {@link A17C09Vo}
     */
    @Override
    public A17C09Vo a17c09(A17C09Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C09Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C09Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 查询考生成绩单签字信息
     *
     * @param bo bo
     * @return {@link A17C10Vo}
     */
    @Override
    public A17C10Vo a17c10(A17C10Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C10Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C10Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 身份信息比对
     *
     * @param bo bo
     * @return {@link A17C51Vo}
     */
    @Override
    public A17C51Vo a17c51(A17C51Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C51Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C51Vo vo = (A17C51Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C51VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试项目开始
     *
     * @param bo bo
     * @return {@link A17C52Vo}
     */
    @Override
    public A17C52Vo a17c52(A17C52Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C52Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C52Vo vo = (A17C52Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C52VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试扣分
     *
     * @param bo bo
     * @return {@link A17C53Vo}
     */
    @Override
    public A17C53Vo a17c53(A17C53Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C53Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C53Vo vo = (A17C53Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C53VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试过程图片
     *
     * @param bo bo
     * @return {@link A17C54Vo}
     */
    @Override
    public A17C54Vo a17c54(A17C54Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C54Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C54Vo vo = (A17C54Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C54VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试项目结束
     *
     * @param bo bo
     * @return {@link A17C55Vo}
     */
    @Override
    public A17C55Vo a17c55(A17C55Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C55Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C55Vo vo = (A17C55Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C55VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试科目考试结束
     *
     * @param bo bo
     * @return {@link A17C56Vo}
     */
    @Override
    public A17C56Vo a17c56(A17C56Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C56Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17C56Vo vo = (A17C56Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17C56VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 查询成绩单上需打印照片
     *
     * @param bo bo
     * @return {@link A17C57Vo}
     */
    @Override
    public A17C57Vo a17c57(A17C57Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C57Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C57Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 读取考生照片信息
     *
     * @param bo bo
     * @return {@link A17C58Vo}
     */
    @Override
    public A17C58Vo a17c58(A17C58Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C58Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C58Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 暂停考场考试 (未申请)
     *
     * @param bo bo
     * @return {@link A17C59Vo}
     */
    @Override
    public A17C59Vo a17c59(A17C59Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C59Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C59Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 暂停考生考试 (未申请)
     *
     * @param bo bo
     * @return {@link A17C60Vo}
     */
    @Override
    public A17C60Vo a17c60(A17C60Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17C60Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17C60Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 考生签到
     *
     * @param bo bo
     * @return {@link A17CB2Vo}
     */
    @Override
    public A17CB2Vo a17cb2(A17CB2Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17CB2Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17CB2Vo vo = (A17CB2Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        // 根据监管接口处理业务逻辑
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 随机安排考试
     *
     * @param bo bo
     * @return {@link A17CB3Vo}
     */
    @Override
    public A17CB3Vo a17cb3(A17CB3Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17CB3Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑
        A17CB3Vo vo = (A17CB3Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
        if (!vo.getHead().getCode().equals("1")) {
            throw new FailException(A17CB3VoEnum.findByCode(vo.getHead().getCode()).getMessage() + "|" + vo.getHead().getMessage());
        }
        // 返回监管底层数据
        return vo;
    }

    /**
     * 考试误判申请 (未申请)
     *
     * @param bo bo
     * @return {@link A17CB4Vo}
     */
    @Override
    public A17CB4Vo a17cb4(A17CB4Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17CB4Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17CB4Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }

    /**
     * 读取收费信息
     *
     * @param bo bo
     * @return {@link A17CC1Vo}
     */
    @Override
    public A17CC1Vo a17cc1(A17CC1Bo bo) {
        CdxxEnum cdxxEnum = CdxxEnum.findByClazz(bo.getClass());
        CommonOldBo<A17CC1Bo> commonOldBo = httpOldHandler.getCommonBo(bo, cdxxEnum);
        JSONObject result = httpOldHandler.sendData(commonOldBo);
        // 根据监管接口处理业务逻辑

        // 返回监管底层数据
        return (A17CC1Vo) httpOldHandler.getVo(result, cdxxEnum.getClazzVo());
    }
}
