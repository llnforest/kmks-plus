package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 明细分组信息业务对象 w2_groups
 *
 * @author lynn
 * @date 2023-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2GroupsBo extends BaseEntity {

    /**
     * 分组序号
     */
    @NotBlank(message = "分组序号不能为空", groups = { EditGroup.class })
    private String xh;

    /**
     * 考试地点
     */
    @NotBlank(message = "考试地点不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksdd;

    /**
     * 考试场次
     */
    @NotBlank(message = "考试场次不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscc;

    /**
     * 考试科目
     */
    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;

    /**
     * 考试车型
     */
    @NotBlank(message = "考试车型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscx;

    /**
     * 考试员
     */
    @NotBlank(message = "考试员不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xm;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zjhm;

    /**
     * 考试日期
     */
    @NotBlank(message = "考试日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksrq;

    /**
     * 考试项目
     */
    @NotBlank(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xmmc;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kchp;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dlr;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String djrq;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lsh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkzh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksyy;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksyybh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfyk;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String yyrq;


}
