package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 车辆模型
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class CarModelDto {
    private String modelname;
    private String modeltype;
    private String pointdata;
    private String state;
    private String scode;
}
