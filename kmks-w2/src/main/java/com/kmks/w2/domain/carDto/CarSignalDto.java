package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 车辆信号
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class CarSignalDto {
    private String signalKey;
    private String signalValue;
}
