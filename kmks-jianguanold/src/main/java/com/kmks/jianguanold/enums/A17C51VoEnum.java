package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C51VoEnum {

    NO_APPOINTMENT("-1", "无预约信息"),
    NOT_TODAY("-2", "约考日期不是今天"),
    EXAM_LIMIT_REACHED("-3", "已满当天允许考试次数"),
    INVALID_CANDIDATE("-4", "考生身份不合法"),
    INVALID_SUBJECT_SYSTEM("-5", "科目考试系统不合法"),
    NO_GROUP_INFO("-6", "无考生分组信息"),
    MISMATCHED_GROUP("-7", "分组考试员和考生不匹配"),
    INVALID_EXAM_CENTER("-8", "考场信息不合法"),
    INVALID_EXAMINER("-9", "考试员信息不合法"),
    INVALID_SYSTEM_TIME("-10", "科目考试系统时间不合法"),
    DUPLICATE_VERIFICATION("-90", "已进行了考试身份验证，无需重复验证"),
    SYSTEM_ERROR("$E", "系统异常"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C51VoEnum findByCode(String code) {
        for (A17C51VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C51VoEnum.OTHER_ERROR;
    }
}
