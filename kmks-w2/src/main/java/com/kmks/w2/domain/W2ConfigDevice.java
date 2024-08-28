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
 * 设备合码器对象 w2_config_device
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_config_device")
public class W2ConfigDevice {

    private static final long serialVersionUID=1L;

    /**
     * 设备号
     */
    @TableId("deviceno")
    private Long deviceno;
    /**
     * 设备IP地址
     */
    private String deviceip;
    /**
     * 合码器端口号
     */
    private String deviceport;
    /**
     * 合码器用户名
     */
    private String devicename;
    /**
     * 合码器密码
     */
    private String devicepassword;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
