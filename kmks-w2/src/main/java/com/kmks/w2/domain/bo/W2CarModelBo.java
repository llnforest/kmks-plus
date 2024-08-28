package com.kmks.w2.domain.bo;

import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆模型业务对象 w2_carmodel
 *
 * @author ghgd
 * @date 2023-03-14
 */

@Data
public class W2CarModelBo {

    /**
     * 主键
     */
    @Column(value = "主键")
    private String id;
    /**
     * 车模名称
     */
    @NotBlank(message = "车模名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "车模名称")
    private String modelname;

    /**
     * 车模类型
     */
    @NotBlank(message = "车模类型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "车模类型")
    private String modeltype;

    /**
     * 车模点位树
     */
    @NotBlank(message = "车模点位树不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "车模点位树")
    private String pointdata;

    /**
     * 状态
     */
    private String state;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "状态")
    private String state1;

    /**
     * $column.columnComment
     */
    @Column(value = "校验位")
    private String scode;

    /**
     * 车模导入点位数
     */
    @NotBlank(message = "车模导入点位数不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "车模导入点位数")
    private String temppointdata;

    /**
     * 车牌号
     */
    @NotBlank(message = "车牌号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column(value = "车牌号")
    private String cph;

    /**
     * 车模标记
     */
    private Long relationId;

}
