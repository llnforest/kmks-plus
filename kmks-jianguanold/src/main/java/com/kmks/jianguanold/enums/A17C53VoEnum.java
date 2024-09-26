package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C53VoEnum {

    DUPLICATE_RECORD("0", "已存在同一时间的同一扣分记录"),
    NO_PROJECT_START_INFO("-1", "无当前考试项目开始信息"),
    DEDUCTION_TIME_EXCEEDS_PROJECT_START("-2", "扣分时间大于项目开始时间"),
    PROJECT_MISMATCH("-3", "考试项目与扣分项不符"),
    CURRENT_PROJECT_CODE_REQUIRED("-4", "项目考试过程中，请传入当前考试项目代码"),
    IDENTITY_NOT_VERIFIED("-5", "考生未进行身份认证"),
    DEDUCTION_TIME_ERROR("-6", "扣分时间写入错误"),
    ILLEGAL_MODIFICATION("-7", "项目考试过程信息记录被非法篡改"),
    NO_EXAM_CENTER_RECORD("-12", "未找到考场记录"),
    NO_VEHICLE_INFO("-13", "未找到考车信息"),
    INFO_MUST_BE_UPLOADED_BY_VEHICLE("-15", "考试过程信息必须由考车上传"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C53VoEnum findByCode(String code) {
        for (A17C53VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C53VoEnum.OTHER_ERROR;
    }
}
