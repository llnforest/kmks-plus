package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C54VoEnum {

    NO_PROJECT_START_INFO("-1", "无当前考试项目开始信息"),
    IDENTITY_MISMATCH("-2", "考生身份证明号码与考生预约信息不符"),
    INCORRECT_PROJECT("-3", "考试项目不正确"),
    DEDUCTION_TIME_ERROR("-4", "扣分时间写入错误"),
    PHOTO_ALREADY_CAPTURED("-90", "同一时间已传入抓拍照片"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C54VoEnum findByCode(String code) {
        for (A17C54VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C54VoEnum.OTHER_ERROR;
    }
}
