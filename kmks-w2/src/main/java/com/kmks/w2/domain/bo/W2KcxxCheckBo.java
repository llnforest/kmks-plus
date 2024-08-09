package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考车自检业务对象 w2_kcxx_check
 *
 * @author Lynn
 * @date 2024-06-19
 */

@Data
public class W2KcxxCheckBo {

    @NotNull(message = "ID不能为空", groups = {  EditGroup.class })
    private Long id;
    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;

    /**
     * 自检结果
     */
    @NotNull(message = "自检结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long checkResult;

    /**
     * 自检内容
     */
    @NotBlank(message = "自检内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String checkContent;

    /**
     * 自检时间
     */
    @NotNull(message = "自检时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date checkTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
