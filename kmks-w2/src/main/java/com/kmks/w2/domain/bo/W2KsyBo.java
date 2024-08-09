package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考官信息业务对象 w2_ksy
 *
 * @author lynn
 * @date 2023-04-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2KsyBo extends BaseEntity {

    /**
     * 序号
     */
    @NotBlank(message = "序号不能为空", groups = { EditGroup.class })
    private String xh;

    /**
     * 所属发证机关
     */
    @NotBlank(message = "所属发证机关不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sszd;

    /**
     * 管理部门
     */
    @NotBlank(message = "管理部门不能为空", groups = { AddGroup.class, EditGroup.class })
    private String glbm;

    /**
     * 证件名称
     */
    @NotBlank(message = "证件名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfzmmc;

    /**
     * 身份证明号码
     */
    @NotBlank(message = "身份证明号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfzmhm;

    /**
     * 驾驶证档案编号
     */
    @NotBlank(message = "驾驶证档案编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dabh;

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
     * 出生日期
     */
    @NotBlank(message = "出生日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String csrq;

    /**
     * 考试准驾车型范围
     */
    @NotBlank(message = "考试准驾车型范围不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkcx;

    /**
     * 考试员证发证日期
     */
    @NotBlank(message = "考试员证发证日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fzrq;

    /**
     * 考试员证有效期止
     */
    @NotBlank(message = "考试员证有效期止不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kszyxqz;

    /**
     * 考试员证状态
     */
    private String ksyzt;

    /**
     * 工作单位
     */
    @NotBlank(message = "工作单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gzdw;

    /**
     * 经办人
     */
    @NotBlank(message = "经办人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jbr;

    /**
     * 考试员证发证单位
     */
    @NotBlank(message = "考试员证发证单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fzjg;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 更新时间
     */
    private String gxsj;


}
