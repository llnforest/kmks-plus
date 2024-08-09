package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 车辆自检
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class CarSelfCheckDto {
    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空")
    private String kch;

    /**
     * 自检内容
     */
    @NotBlank(message = "自检内容不能为空")
    private String checkContent;

    /**
     * 考车IP
     */
    @NotBlank(message = "考车IP不能为空")
    private String carIp;

    /**
     * Mac地址
     */
    @NotBlank(message = "MAC地址不能为空")
    private String carMac;

    /**
     * 自检结果
     */
    @NotNull(message = "自检结果")
    private Long checkResult;



}
