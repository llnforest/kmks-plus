package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C56VoEnum {

    NO_SUBJECT_INFO("-1", "无当前科目考试信息"),
    ID_MISMATCH("-2", "考生身份证明号码与考生预约信息不符"),
    INCORRECT_END_TIME("-3", "考试结束时间不正确"),
    INSUFFICIENT_PHOTOS("-4", "考试过程中拍摄照片数量少于3张"),
    INVALID_EXAM_ITEM("-5", "考试项目不符合要求"),
    UNFINISHED_EXAM_ITEM("-6", "存在未结束的考试项目"),
    EMPTY_EXAM_RESULT("-7", "传输的考试成绩不可为空"),
    INCONSISTENT_RESULTS("-91", "考试成绩计算不一致"),
    WAIT_FOR_NIGHT_EXAM("-92", "日间考试已结束等待进行夜间考试"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C56VoEnum findByCode(String code) {
        for (A17C56VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C56VoEnum.OTHER_ERROR;
    }
}
