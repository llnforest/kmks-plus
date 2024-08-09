package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目监控业务对象 w2_config_switch
 *
 * @author lynn
 * @date 2023-05-04
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2ConfigSwitchBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 项目代码
     */
    @NotBlank(message = "项目代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectcode;

    /**
     * 设备IP
     */
    @NotBlank(message = "设备IP不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deviceip;

    /**
     * 设备端口
     */
    @NotBlank(message = "设备端口不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deviceport;

    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deviceusername;

    /**
     * 设备密码
     */
    @NotBlank(message = "设备密码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String devicepassword;

    /**
     * 通道类型
     */
    @NotNull(message = "通道类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long channeltype;

    /**
     * 远程设备通道号
     */
    @NotNull(message = "远程设备通道号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long devicechannel;

    /**
     * 传输协议
     */
    @NotNull(message = "传输协议不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long protocol;

    /**
     * 厂商类型
     */
    @NotNull(message = "厂商类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long vendortype;

    /**
     * 码流类型
     */
    @NotNull(message = "码流类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long bitstreamtype;


}
