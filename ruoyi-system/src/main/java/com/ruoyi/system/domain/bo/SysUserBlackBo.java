package com.ruoyi.system.domain.bo;

import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 黑名单管理业务对象 sys_user_black
 *
 * @author Lynn
 * @date 2023-03-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserBlackBo extends BaseEntity {

    /**
     * 编号
     */
    @NotNull(message = "编号不能为空", groups = { EditGroup.class })
    @Column("ID")
    private Long id;

    /**
     * 校验位
     */
    @NotBlank(message = "校验位不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("校验位")
    private String validCode;

    /**
     * 对象名称
     */
    @NotBlank(message = "对象名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("对象名称")
    private String blackName;

    /**
     * 对象类型
     */
    @NotBlank(message = "对象类型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "对象类型",dict = "black_lock_type")
    private String blackType;

    /**
     * 锁定状态
     */
    @NotBlank(message = "锁定状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "锁定状态",dict = "black_lock_status")
    private String isLock;

    /**
     * 锁定原因
     */
    @NotBlank(message = "锁定原因不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("锁定原因")
    private String remark;


}
