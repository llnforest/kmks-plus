package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备合码器业务对象 w2_config_device
 *
 * @author lynn
 * @date 2023-05-04
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2ConfigDeviceBo extends BaseEntity {


    /**
     * 设备号
     */
    @NotNull(message = "设备号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deviceno;

    /**
     * 设备IP地址
     */
    @NotBlank(message = "设备IP地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deviceip;

    /**
     * 合码器端口号
     */
    @NotBlank(message = "合码器端口号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deviceport;

    /**
     * 合码器用户名
     */
    @NotBlank(message = "合码器用户名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String devicename;

    /**
     * 合码器密码
     */
    @NotBlank(message = "合码器密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String devicepassword;


}
