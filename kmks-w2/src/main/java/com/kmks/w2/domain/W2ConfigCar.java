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
 * 车辆合码器对象 w2_config_car
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_config_car")
public class W2ConfigCar {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 车号
     */
    private String carno;
    /**
     * 合码器设备号
     */
    private Long deviceno;
    /**
     * 合码器输出口
     */
    private Long deviceoutputno;
    /**
     * 解码通道
     */
    private Long videochannel;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
