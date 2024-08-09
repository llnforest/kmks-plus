package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 新扣分代码
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class CenterKfDto {
    /**
     * 扣分代码
     **/
    private String kfdm;
    /**
     * 扣分评判项
    **/
    private String kfmc;
    /**
     * 扣分值
     **/
    private Long kf;
    /**
     * 参数值
     **/
    private String value;
}
