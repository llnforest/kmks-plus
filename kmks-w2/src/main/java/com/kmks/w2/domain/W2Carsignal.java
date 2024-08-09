package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆信号对象 w2_carsignal
 *
 * @author Lynn
 * @date 2024-05-29
 */
@Data
@TableName("w2_carsignal")
public class W2Carsignal {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 信号标识
     */
    private String signalKey;
    /**
     * 信号数值
     */
    private String signalValue;
    /**
     * 信号名称
     */
    private String signalName;
    /**
     * 信号自检
     */
    private String signalNote;

}
