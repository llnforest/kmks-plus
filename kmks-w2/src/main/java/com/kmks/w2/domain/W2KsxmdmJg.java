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
 * 项目代码对象 w2_ksxmdm_jg
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_ksxmdm_jg")
public class W2KsxmdmJg {

    private static final long serialVersionUID=1L;

    /**
     * 项目代码
     */
    @TableId
    private String id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * 项目序号
     */
    private String custxh;
    /**
     * $column.columnComment
     */
    private Long kmtime1;
    /**
     * $column.columnComment
     */
    private Long kmtime2;
    /**
     * $column.columnComment
     */
    private Long kmtime3;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
