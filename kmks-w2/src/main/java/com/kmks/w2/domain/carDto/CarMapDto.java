package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 轨迹GPS
 *
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
public class CarMapDto {
    /**
     * sql
     */
    @NotBlank(message = "SQL语句不能为空")
    private String sql;

}
