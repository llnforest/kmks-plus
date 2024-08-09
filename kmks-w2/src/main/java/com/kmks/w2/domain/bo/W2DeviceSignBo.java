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
 * 签到设备业务对象 w2_device_sign
 *
 * @author lynn
 * @date 2024-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2DeviceSignBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    @Column(value="编号")
    private Long signId;

    /**
     * 签到设备
     */
    @NotBlank(message = "签到设备不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="签到设备")
    private String signName;

    /**
     * 设备IP
     */
    @NotBlank(message = "设备IP不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="设备IP")
    private String signIp;

    /**
     * 设备MAC
     */
    @NotBlank(message = "设备MAC不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="设备MAC")
    private String signMac;

    /**
     * 设备备注
     */
    @Column(value="设备备注")
    private String remark;

    /**
     * 设备状态
     */
    @NotBlank(message = "设备状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value="设备状态")
    private String signStatus;


}
