package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 路线管理业务对象 w2_lineconfig
 *
 * @author Lynn
 * @date 2023-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2LineconfigBo extends BaseEntity {

    /**
     * 路线
     */
    @NotNull(message = "路线不能为空", groups = { AddGroup.class,EditGroup.class })
    private Long line;

    /**
     * 监管代码
     */
    @NotBlank(message = "监管代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linecode;

    /**
     * 线路状态
     */
    @NotNull(message = "线路状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linezt;


}
