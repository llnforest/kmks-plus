package com.kmks.w2.domain.bo;

import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 闸机设备业务对象 w2_device_gate
 *
 * @author Lynn
 * @date 2024-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2DeviceGateBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    @Column(value="编号")
    private Long gateId;

    /**
     * 闸机设备
     */
    @NotBlank(message = "闸机设备不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="闸机设备")
    private String gateName;

    /**
     * 闸机IP
     */
    @NotBlank(message = "设备IP不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="闸机IP")
    private String gateIp;

    /**
     * 闸机MAC
     */
    @NotBlank(message = "设备MAC不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="闸机MAC")
    private String gateMac;

    /**
     * 设备备注
     */
    @Column(value="设备备注")
    private String remark;

    /**
     * 闸机状态
     */
    @NotBlank(message = "设备状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="闸机状态")
    private String gateStatus;


}
