package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆信息业务对象 w2_carinfo
 *
 * @author lynn
 * @date 2023-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2CarinfoBo extends BaseEntity {

    /**
     * 序号
     */
    @NotBlank(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xh;

    /**
     * 号牌类型
     */
    @NotBlank(message = "号牌类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hpzl;

    /**
     * 号牌号码
     */
    @NotBlank(message = "号牌号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String hphm;

    /**
     * 准驾车型
     */
    @NotBlank(message = "准驾车型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String syzjcx;

    /**
     * 车辆类型
     */
    @NotBlank(message = "车辆类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cllx;

    /**
     * 车辆品牌
     */
    @NotBlank(message = "车辆品牌不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clpp;

    /**
     * 初次登记日期
     */
    @NotBlank(message = "初次登记日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ccdjrq;

    /**
     * 强制报废日期
     */
    @NotBlank(message = "强制报废日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qzbfqz;

    /**
     * 发证机关
     */
    @NotBlank(message = "发证机关不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fzjg;

    /**
     * 车辆状态
     */
    @NotBlank(message = "车辆状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zt;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksczt;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shr;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ipdz;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gkjmac;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gkjbh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gkjdk;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cczdip;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cczdmac;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cczdbh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cczddk;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sskcxh;


}
