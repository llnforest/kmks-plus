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
 * 项目监控对象 w2_config_switch
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = false)
@TableName("w2_config_switch")
public class W2ConfigSwitch {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 项目代码
     */
    private String projectcode;
    /**
     * 设备IP
     */
    private String deviceip;
    /**
     * 设备端口
     */
    private String deviceport;
    /**
     * 设备名称
     */
    private String deviceusername;
    /**
     * 设备密码
     */
    private String devicepassword;
    /**
     * 通道类型
     */
    private Long channeltype;
    /**
     * 远程设备通道号
     */
    private Long devicechannel;
    /**
     * 传输协议
     */
    private Long protocol;
    /**
     * 厂商类型
     */
    private Long vendortype;
    /**
     * 码流类型
     */
    private Long bitstreamtype;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
