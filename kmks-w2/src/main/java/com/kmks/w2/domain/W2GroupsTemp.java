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
 * 查询下载信息对象 w2_groups_temp
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_groups_temp")
public class W2GroupsTemp{

    private static final long serialVersionUID=1L;

    /**
     * 流水号
     */
    @TableId(value = "lsh")
    private String lsh;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别
     */
    private String xb;
    /**
     * 准考证号
     */
    private String zkzmbh;
    /**
     * 考试原因
     */
    private String ksyy;
    /**
     * 考试日期
     */
    private String ksrq;
    /**
     * 考试车型
     */
    private String kscx;
    /**
     * 考试场次
     */
    private String kscc;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * 考试员1
     */
    private String ksy1;
    /**
     * 考试员2
     */
    private String ksy2;
    /**
     * 驾校代码
     */
    private String dlr;
    /**
     * 驾校名称
     */
    private String jxmc;
    /**
     * 考试项目
     */
    private String ksxm;
    /**
     * 预约次数
     */
    private String yycs;
    /**
     * 考试原因代码
     */
    private String ksyybh;
    /**
     * 是否夜考
     */
    private String sfyk;
    /**
     * 本次预约考试次数
     */
    private String bcyykscs;
    /**
     * 管理部门
     */
    private String glbm;
    /**
     * 经办人
     */
    private String jbr;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
