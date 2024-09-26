package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试数据业务对象 w2_user_test
 *
 * @author lynn
 * @date 2024-09-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2UserTestBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 考生姓名
     */
    @NotBlank(message = "考生姓名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String xm;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String zjhm;

    /**
     * 考试车型
     */
    @NotBlank(message = "考试车型不能为空", groups = {AddGroup.class, EditGroup.class})
    private String kscx;

    /**
     * 约考日期
     */
    private String ykrq;


}
