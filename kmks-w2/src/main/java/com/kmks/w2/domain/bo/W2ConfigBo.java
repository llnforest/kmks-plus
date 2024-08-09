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
 * 驾考参数业务对象 w2_config
 *
 * @author ruoyi
 * @date 2023-03-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2ConfigBo extends BaseEntity {

    /**
     * 编码
     */
    @NotNull(message = "编码不能为空", groups = { EditGroup.class })
    @Column("编码")
    private Long lIncode;

    /**
     * 标志参数
     */
    @NotNull(message = "标志参数不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("标志参数")
    private Long iFlag;

    /**
     * 标志类型
     */
    @NotBlank(message = "标志类型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("标志类型")
    private String sFlag;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("名称")
    private String sName;

    /**
     * 标志值
     */
    @NotNull(message = "标志值不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("标志值")
    private Long iValue;

    /**
     * 标志值
     */
    @NotBlank(message = "标志值不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("标志值")
    private String sValue;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("备注")
    private String sBeizhu;


}
