package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 查询下载信息业务对象 w2_groups_temp
 *
 * @author lynn
 * @date 2023-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2GroupsTempBo extends BaseEntity {

    /**
     * 流水号
     */
    @NotBlank(message = "流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lsh;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zjhm;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xm;

    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xb;

    /**
     * 准考证号
     */
    @NotBlank(message = "准考证号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkzmbh;

    /**
     * 考试原因
     */
    @NotBlank(message = "考试原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksyy;

    /**
     * 考试日期
     */
    @NotBlank(message = "考试日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksrq;

    /**
     * 考试车型
     */
    @NotBlank(message = "考试车型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscx;

    /**
     * 考试场次
     */
    @NotBlank(message = "考试场次不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscc;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xmmc;

    /**
     * 考试员1
     */
    @NotBlank(message = "考试员1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy1;

    /**
     * 考试员2
     */
    @NotBlank(message = "考试员2不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy2;

    /**
     * 驾校代码
     */
    @NotBlank(message = "驾校代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dlr;

    /**
     * 驾校名称
     */
    @NotBlank(message = "驾校名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jxmc;

    /**
     * 考试项目
     */
    @NotBlank(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * 预约次数
     */
    @NotBlank(message = "预约次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String yycs;

    /**
     * 考试原因代码
     */
    @NotBlank(message = "考试原因代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksyybh;

    /**
     * 是否夜考
     */
    @NotBlank(message = "是否夜考不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfyk;

    /**
     * 本次预约考试次数
     */
    @NotBlank(message = "本次预约考试次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bcyykscs;

    /**
     * 管理部门
     */
    @NotBlank(message = "管理部门不能为空", groups = { AddGroup.class, EditGroup.class })
    private String glbm;

    /**
     * 经办人
     */
    @NotBlank(message = "经办人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jbr;


}
