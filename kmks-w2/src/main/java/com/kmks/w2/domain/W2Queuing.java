package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 排队信息对象 w2_queuing
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_queuing")
public class W2Queuing {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 考试编号
     */
    private String ksbh;
    /**
     * 考生姓名
     */
    private String xm;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 驾校名称
     */
    private String jxmc;
    /**
     * 考车编号
     */
    private String kcbh;
    /**
     * 车牌号码
     */
    private String kchp;
    /**
     * 线路
     */
    private Long rLine;
    /**
     * 锁定状态
     */
    private Long iLock;
    /**
     * 签到状态
     */
    private Long sign;
    /**
     * 考试状态
     */
    private String kszt;
    /**
     * 第几次
     */
    private Long djc;
    /**
     * 缴费
     */
    private String sqks;
    /**
     * 成绩
     */
    private Long score;
    /**
     * 夜考
     */
    private String sfyk;
    /**
     * 分配项目
     */
    private String ksxm;
    /**
     * 完成项目
     */
    private String wcxm;
    /**
     * 考试车型
     */
    private String kscx;
    /**
     * 考试员姓名
     */
    private String kgname;
    /**
     * 考试员证件
     */
    private String kg;
    /**
     * 安全员
     */
    private String sSafe;
    /**
     * 安全员证件
     */
    private String sSafeZjhm;
    /**
     * $column.columnComment
     */
    private String zkzh;
    /**
     * $column.columnComment
     */
    private String ykxms;
    /**
     * $column.columnComment
     */
    private String zkxms;
    /**
     * 排队序号
     */
    private Long bdxh;
    /**
     * 考试次数
     */
    private Long kscs;
    /**
     * 预约次数
     */
    private Long ykcs;
    /**
     * $column.columnComment
     */
    private String wcxms;
    /**
     * $column.columnComment
     */
    private Long kscj;
    /**
     * $column.columnComment
     */
    private Date kssj;
    /**
     * $column.columnComment
     */
    private String cdxmbh;
    /**
     * $column.columnComment
     */
    private String sfyz;
    /**
     * 日考夜考是否分开 默认可以一起考
     */
    private String kslx;
    /**
     * 驾校代码/考场编号
     */
    private String jxdm;
    /**
     * 考试日期
     */
    private Date ksrq;
    /**
     * 分车状态
     */
    private String zt;
    /**
     * $column.columnComment
     */
    private Date sqsj;
    /**
     * $column.columnComment
     */
    private String qxlx;
    /**
     * $column.columnComment
     */
    private String kcxh;
    /**
     * $column.columnComment
     */
    private String flag;
    /**
     * $column.columnComment
     */
    private String jszt;
    /**
     * $column.columnComment
     */
    private String lsh;
    /**
     * $column.columnComment
     */
    private String zkxmdm;
    /**
     * 指纹
     */
    private String finger;
    /**
     * $column.columnComment
     */
    private String zsfhg;
    /**
     * $column.columnComment
     */
    private Long zcs;
    /**
     * $column.columnComment
     */
    private String kfxm;
    /**
     * $column.columnComment
     */
    private String kcbs;
    /**
     * $column.columnComment
     */
    private String fieldid;
    /**
     * $column.columnComment
     */
    private String kg2;
    /**
     * 考试成绩
     */
    private Long kscc;
    /**
     * $column.columnComment
     */
    private String signcontent;
    /**
     * $column.columnComment
     */
    private String ksyy;
    /**
     * $column.columnComment
     */
    private String jbr;
    /**
     * $column.columnComment
     */
    private String glbm;
    /**
     * 预约次数
     */
    private String yycs;
    /**
     * 本次预约考试次数
     */
    private String bcyykscs;
    /**
     * $column.columnComment
     */
    private String linecode;
    /**
     * $column.columnComment
     */
    private Date xmkssj;
    /**
     * $column.columnComment
     */
    private String message;
    /**
     * $column.columnComment
     */
    private Long iVoiceTimes;
    /**
     * $column.columnComment
     */
    private Date dVoiceDate;
    /**
     * $column.columnComment
     */
    private Long iVoiceReady;
    /**
     * $column.columnComment
     */
    private Long cwcs;
    /**
     * $column.columnComment
     */
    private String kskm;

    /**
     * 计算总数
     */
    @TableField(value = "count(*)", select = false, insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER, whereStrategy = FieldStrategy.NEVER)
    private Long totalNum;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
