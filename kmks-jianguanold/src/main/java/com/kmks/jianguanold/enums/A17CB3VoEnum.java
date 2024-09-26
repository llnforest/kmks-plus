package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17CB3VoEnum {

    INVALID_CAR_INFO("-1", "考车信息不正确"),
    INVALID_VENUE_INFO("-2", "考场信息不正确"),
    INVALID_EXAMINER_INFO("-3", "考试员信息不正确"),
    RANDOM_ALLOCATION_ERROR("-4", "随机分配出现错误"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17CB3VoEnum findByCode(String code) {
        for (A17CB3VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17CB3VoEnum.OTHER_ERROR;
    }
}
