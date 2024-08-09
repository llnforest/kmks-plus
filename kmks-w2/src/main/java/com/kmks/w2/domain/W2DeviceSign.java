package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 签到设备对象 w2_device_sign
 *
 * @author lynn
 * @date 2024-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_device_sign")
public class W2DeviceSign {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId("sign_id")
    private Long signId;
    /**
     * 签到设备
     */
    private String signName;
    /**
     * 设备IP
     */
    private String signIp;
    /**
     * 设备MAC
     */
    private String signMac;
    /**
     * 设备备注
     */
    private String remark;
    /**
     * 设备状态
     */
    private String signStatus;

}
