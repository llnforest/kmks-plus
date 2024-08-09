package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基础编码业务对象 w2_system
 *
 * @author ruoyi
 * @date 2023-03-27
 */

@Data
public class W2SystemBo {

    /**
     * 编号
     */
    @NotNull(message = "编号不能为空", groups = {  EditGroup.class })
    private Long nid;

    /**
     * 考场信息
     */
    private Long type;

    /**
     * 驾校代码
     */
    @NotBlank(message = "驾校代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paramdm;

    /**
     * 驾校名称
     */
    @NotBlank(message = "驾校名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paramname;

    /**
     * 备注
     */
    private String bz;

    /**
     * 场次,0:全天 1:上午 2:下午
     */
    @NotBlank(message = "场次,0:全天 1:上午 2:下午 不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paramtype;

    private String jxqc;

    private String maxdm;


}
