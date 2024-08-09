package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 扣分信息
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class DeductPointsDto {
    private String id;
    private String gakfdm;
    private String kfmc;
    private String kf;
    private String flag;
}
