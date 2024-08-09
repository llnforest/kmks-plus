package com.kmks.w2.domain.carDto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 场地地图
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class FieldMapDto {
    private String fieldname;
    private String fieldid;
    private String fieldtype;
    private String pointCount;
    private String pointData;
    private String state;
    private String lineno;
    private String state2;
    private String scode;
}
