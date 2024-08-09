package com.kmks.w2.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.sql.Blob;
import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全员管理业务对象 gh_safepeople
 *
 * @author ruoyi
 * @date 2023-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class GhSafepeopleBo extends BaseEntity {

    /**
     * 安全员姓名
     */
    @NotBlank(message = "安全员姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sName;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sZjhm;

    /**
     * 照片存储
     */
    private byte[] sZp;

    /**
     * 照片路径
     */
    private String sZpUrl;

    /**
     * 状态
     */
//    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long iZt;

    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;

    /**
     * 考车牌号
     */
    @NotBlank(message = "考车牌号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kchp;


}
