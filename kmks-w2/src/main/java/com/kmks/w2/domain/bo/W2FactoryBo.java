package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考点信息业务对象 w2_factory
 *
 * @author lynn
 * @date 2023-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2FactoryBo extends BaseEntity {

    /**
     * 考场序号
     */
    @NotBlank(message = "考场序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xh;

    /**
     * 考场名称
     */
    @NotBlank(message = "考场名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcmc;

    /**
     * 适用准驾车型范围
     */
    @NotBlank(message = "适用准驾车型范围不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kkcx;

    /**
     * 管理部门
     */
    @NotBlank(message = "管理部门不能为空", groups = { AddGroup.class, EditGroup.class })
    private String glbm;

    /**
     * 发证机关
     */
    @NotBlank(message = "发证机关不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fzjg;

    /**
     * 考场代码
     */
    @NotBlank(message = "考场代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcdddh;


}
