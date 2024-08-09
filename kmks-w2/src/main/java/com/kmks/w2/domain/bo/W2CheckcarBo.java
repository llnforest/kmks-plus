package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆自检业务对象 w2_checkcar
 *
 * @author ruoyi
 * @date 2023-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2CheckcarBo extends BaseEntity {

    /**
     * 车辆编号
     */
    @NotBlank(message = "车辆编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sCarno;

    /**
     * 考车自检时间
     */
    @NotBlank(message = "考车自检时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sTime;

    /**
     * 考车自检状态
     */
    @NotBlank(message = "考车自检状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sValue;

    /**
     * 考车自检说明
     */
    @NotBlank(message = "考车自检说明不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sNote;

    /**
     * 自检日期
     */
    @NotNull(message = "自检日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date dtFs;


}
