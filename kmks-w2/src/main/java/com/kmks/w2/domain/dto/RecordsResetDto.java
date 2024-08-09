package com.kmks.w2.domain.dto;

import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Star
 * @description: TODO
 * @date 2023/4/18 16:18
 */
@Data
public class RecordsResetDto {
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    @NotNull(message = "误判选择不能为空", groups = { EditGroup.class })
    private Integer wpxz;

    private String wpyy;
}
