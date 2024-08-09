package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 闸机设备对象 w2_device_gate
 *
 * @author Lynn
 * @date 2024-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_device_gate")
public class W2DeviceGate {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId("gate_id")
    private Long gateId;
    /**
     * 闸机设备
     */
    private String gateName;
    /**
     * 闸机IP
     */
    private String gateIp;
    /**
     * 闸机MAC
     */
    private String gateMac;
    /**
     * 设备备注
     */
    private String remark;
    /**
     * 闸机状态
     */
    private String gateStatus;

}
