package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 扣分代码业务对象 w2_ksxmkfdm_jg
 *
 * @author lynn
 * @date 2024-04-23
 */

@Data
public class W2KsxmkfdmJgBo {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = {  EditGroup.class })
    private Long id;

    /**
     * 项目代码
     */
    @NotNull(message = "项目代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ksxmdm;

    /**
     * 扣分代码
     */
    @NotBlank(message = "扣分代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gakfdm;

    /**
     * 扣分类别
     */
    @NotBlank(message = "扣分类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gakfmc;

    /**
     * 扣分说明
     */
    @NotBlank(message = "扣分说明不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kfmc;

    /**
     * 扣分值
     */
    @NotNull(message = "扣分值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kf;

    /**
     * 考试项目
     */
    @NotBlank(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * 考试科目
     */
    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;

    /**
     * $column.columnComment
     */
    private String sBeizhu2;

    /**
     * $column.columnComment
     */
    private String ksxmxh;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long flag;


}
