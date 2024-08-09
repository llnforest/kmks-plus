package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目代码业务对象 w2_ksxmdm_jg
 *
 * @author ruoyi
 * @date 2023-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2KsxmdmJgBo extends BaseEntity {

    /**
     * 项目代码
     */
    @NotBlank(message = "项目代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String id;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 考试科目
     */
    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;

    /**
     * 项目序号
     */
    @NotBlank(message = "项目序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String custxh;

    /**
     * $column.columnComment
     */
    private Long kmtime1;

    /**
     * $column.columnComment
     */
    private Long kmtime2;

    /**
     * $column.columnComment
     */
    private Long kmtime3;


}
