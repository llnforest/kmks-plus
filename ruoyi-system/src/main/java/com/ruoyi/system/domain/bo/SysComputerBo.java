package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 管理计算机访问业务对象 sys_computer
 *
 * @author Lynn
 * @date 2024-07-10
 */

@Data
public class SysComputerBo {

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 管理计算机名称
     */
    @NotBlank(message = "管理计算机名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String computerName;

    /**
     * 管理计算机位置
     */
    @NotBlank(message = "管理计算机位置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String computerLocation;

    /**
     * IP地址
     */
    @NotBlank(message = "IP地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ip;

    /**
     * MAC地址
     */
    @NotBlank(message = "MAC地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mac;

    /**
     * 备注
     */
    private String remarks;


}
