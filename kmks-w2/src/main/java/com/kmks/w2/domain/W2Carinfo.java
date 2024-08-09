package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆信息对象 w2_carinfo
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_carinfo")
public class W2Carinfo{

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    private String xh;
    /**
     * 号牌类型
     */
    private String hpzl;
    /**
     * 号牌号码
     */
    private String hphm;
    /**
     * 准驾车型
     */
    private String syzjcx;
    /**
     * 车辆类型
     */
    private String cllx;
    /**
     * 车辆品牌
     */
    private String clpp;
    /**
     * 初次登记日期
     */
    private String ccdjrq;
    /**
     * 强制报废日期
     */
    private String qzbfqz;
    /**
     * 发证机关
     */
    private String fzjg;
    /**
     * 车辆状态
     */
    private String zt;
    /**
     * $column.columnComment
     */
    private String ksczt;
    /**
     * $column.columnComment
     */
    private String shr;
    /**
     * $column.columnComment
     */
    private String ipdz;
    /**
     * $column.columnComment
     */
    private String gkjmac;
    /**
     * $column.columnComment
     */
    private String gkjbh;
    /**
     * $column.columnComment
     */
    private String gkjdk;
    /**
     * $column.columnComment
     */
    private String cczdip;
    /**
     * $column.columnComment
     */
    private String cczdmac;
    /**
     * $column.columnComment
     */
    private String cczdbh;
    /**
     * $column.columnComment
     */
    private String cczddk;
    /**
     * $column.columnComment
     */
    private String sskcxh;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
