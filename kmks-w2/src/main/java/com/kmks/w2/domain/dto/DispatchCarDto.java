package com.kmks.w2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 调度中心车辆数据
 * @author Star
 * @description: TODO
 * @date 2024/6/6 9:09
 */
@Data
@AllArgsConstructor
public class DispatchCarDto {
    /**
     * 考车编号
    **/
    private String kcbh;

    /**
     * 候考人数
     **/
    private Integer peopleNum;

    /**
     * 姓名
     **/
    private String xm;

}
