package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆信号业务对象 w2_carsignal
 *
 * @author Lynn
 * @date 2024-05-29
 */

@Data
public class W2CarsignalBo {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 信号标识
     */
    @NotBlank(message = "信号标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signalKey;

    /**
     * 信号数值
     */
    @NotBlank(message = "信号数值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signalValue;

    /**
     * 信号名称
     */
    @NotBlank(message = "信号名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signalName;

    /**
     * 信号自检
     */
    @NotBlank(message = "信号自检不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signalNote;


}
