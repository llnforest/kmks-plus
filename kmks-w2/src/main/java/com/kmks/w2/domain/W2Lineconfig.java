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
 * 路线管理对象 w2_lineconfig
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_lineconfig")
public class W2Lineconfig{

    private static final long serialVersionUID=1L;

    /**
     * 路线
     */
    @TableId(value = "line")
    private Long line;
    /**
     * 监管代码
     */
    private String linecode;
    /**
     * 线路状态
     */
    private Long linezt;
    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
