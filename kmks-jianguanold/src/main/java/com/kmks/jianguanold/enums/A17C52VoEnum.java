package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C52VoEnum {

    CHEATING_SUSPICION("0", "存在作弊嫌疑，已被取消或暂停考试"),
    NO_IDENTITY_MATCH("-1", "无考生身份比对信息"),
    EXAM_PROJECT_MISMATCH("-2", "考试项目与考试安排信息不符"),
    DEVICE_NOT_REGISTERED("-3", "考试设备未备案"),
    DEVICE_PROJECT_MISMATCH("-4", "考试设备与考试项目不符"),
    DEVICE_STATUS_ERROR("-5", "考试设备使用状态异常"),
    VEHICLE_TYPE_MISMATCH("-6", "考生考试车型与考试设备使用准驾车型范围不符"),
    CHEATING_SUSPICION_CANCELLED("-7", "该考生存在作弊嫌疑，已被暂停/取消考试"),
    START_TIME_TOO_EARLY("-8", "项目开始时间不能小于科目开始时间"),
    UNFINISHED_PROJECTS("-9", "存在未结束的考试项目，不能开始新的项目考试！"),
    PLATE_NUMBER_REQUIRED("-10", "科目三考车号牌不能为空"),
    UNFINISHED_EXAM_SAME_VEHICLE("-11", "同一考车存在未结束考试，不能开始应用于新的考试"),
    NO_EXAM_CENTER_RECORD("-12", "未找到考场记录"),
    NO_VEHICLE_INFO("-13", "未找到考车信息"),
    VEHICLE_MISMATCH("-14", "随机抽取考生的考车与当前考车不一致"),
    INFO_MUST_BE_UPLOADED_BY_VEHICLE("-15", "考试过程信息必须由考车上传"),
    PROJECT_ALREADY_STARTED("-90", "考试项目已经开始，无需重传"),
    SYSTEM_ERROR("$E", "系统异常"),
    NOT_EXAMINED_BY_ALLOCATION("-20", "未按照考试监管的分配进行考试"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C52VoEnum findByCode(String code) {
        for (A17C52VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C52VoEnum.OTHER_ERROR;
    }
}
