package com.kmks.w2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 下发扣分
 * @author Star
 * @description: TODO
 * @date 2024/6/6 9:09
 */
@Data
@AllArgsConstructor
public class JudgeDto {
    /**
     * 考车编号
    **/
    private String kcbh;

    /**
     * 扣分代码
     **/
    private String gakfdm;

    /**
     * 扣分值
     **/
    private String kf;

    /**
     * 扣分信息
     **/
    private String kfxx;

}
