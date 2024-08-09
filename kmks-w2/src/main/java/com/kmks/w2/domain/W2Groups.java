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
 * 明细分组信息对象 w2_groups
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_groups")
public class W2Groups {

    private static final long serialVersionUID=1L;

    /**
     * 分组序号
     */
    @TableId(value = "xh")
    private String xh;
    /**
     * 考试地点
     */
    private String ksdd;
    /**
     * 考试场次
     */
    private String kscc;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * 考试车型
     */
    private String kscx;
    /**
     * 考试员
     */
    private String ksy;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 考试日期
     */
    private String ksrq;
    /**
     * 考试项目
     */
    private String ksxm;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * $column.columnComment
     */
    private String kchp;
    /**
     * $column.columnComment
     */
    private String dlr;
    /**
     * $column.columnComment
     */
    private String djrq;
    /**
     * $column.columnComment
     */
    private String lsh;
    /**
     * $column.columnComment
     */
    private String zkzh;
    /**
     * $column.columnComment
     */
    private String ksyy;
    /**
     * $column.columnComment
     */
    private String ksyybh;
    /**
     * $column.columnComment
     */
    private String sfyk;
    /**
     * $column.columnComment
     */
    private String yyrq;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
