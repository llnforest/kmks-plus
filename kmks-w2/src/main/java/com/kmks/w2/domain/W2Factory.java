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
 * 考点信息对象 w2_factory
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_factory")
public class W2Factory {

    private static final long serialVersionUID=1L;

    /**
     * 考场序号
     */
    private String xh;
    /**
     * 考场名称
     */
    private String kcmc;
    /**
     * 适用准驾车型范围
     */
    private String kkcx;
    /**
     * 管理部门
     */
    private String glbm;
    /**
     * 发证机关
     */
    private String fzjg;
    /**
     * 考场代码
     */
    private String kcdddh;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
