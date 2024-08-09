package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全日志业务对象 sys_safe_log
 *
 * @author ghgd
 * @date 2023-03-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysSafeLogBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户代码
     */
    @NotBlank(message = "用户代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userNo;

    /**
     * 用户姓名
     */
    @NotBlank(message = "用户姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 操作IP
     */
    @NotBlank(message = "操作IP不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userIp;

    /**
     * 业务类别
     */
    @NotBlank(message = "业务类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String category;

    /**
     * 操作类型
     */
    @NotBlank(message = "操作类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String operation;

    /**
     * 操作结果
     */
    @NotBlank(message = "操作结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String state;

    /**
     * 操作内容
     */
    @NotBlank(message = "操作内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * mac地址
     */
    @NotBlank(message = "mac地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mac;

    /**
     * 校验位
     */
    @NotBlank(message = "校验位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 终端机器名
     */
    @NotBlank(message = "终端机器名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hostName;

    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ywtpye;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
