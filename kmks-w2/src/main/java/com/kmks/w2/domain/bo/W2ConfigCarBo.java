package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆合码器业务对象 w2_config_car
 *
 * @author lynn
 * @date 2023-05-04
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2ConfigCarBo extends BaseEntity {


    /**
     * 车号
     */
    @NotBlank(message = "车号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String carno;

    /**
     * 合码器设备号
     */
    @NotNull(message = "合码器设备号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deviceno;

    /**
     * 合码器输出口
     */
    @NotNull(message = "合码器输出口不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deviceoutputno;

    /**
     * 解码通道
     */
    @NotNull(message = "解码通道不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long videochannel;


}
