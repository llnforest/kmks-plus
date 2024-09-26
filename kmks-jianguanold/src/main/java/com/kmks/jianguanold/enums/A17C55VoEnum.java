package com.kmks.jianguanold.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum A17C55VoEnum {

    NO_PROJECT_START_INFO("-1", "无当前考试项目开始信息"),
    IDENTITY_MISMATCH("-2", "考生身份证明号码与考生预约信息不符"),
    INCORRECT_PROJECT("-3", "考试项目不正确"),
    INCORRECT_DEVICE_SERIAL("-4", "考试设备序号不正确"),
    INCORRECT_END_TIME_WRITE("-5", "考试项目结束时间写入错误"),
    IDENTITY_NOT_VERIFIED("-6", "考生未进行身份认证"),
    ILLEGAL_MODIFICATION("-7", "项目考试过程信息记录被非法篡改"),
    MIN_ONE_PHOTO_REQUIRED("-8", "每个考试项目中必须至少抓拍一张照片"),
    INCORRECT_OPERATION_TYPE("-9", "操作类型格式不正确"),
    NO_EXAM_CENTER_RECORD("-12", "未找到考场记录"),
    NO_VEHICLE_INFO("-13", "未找到考车信息"),
    INFO_MUST_BE_UPLOADED_BY_VEHICLE("-15", "考试过程信息必须由考车上传"),
    PROJECT_ALREADY_ENDED("-90", "考试项目已经结束、无需重传"),
    UNIFIED_END_AT_REAL_ROAD_EXAM("-91", "实际道路考试，在完成科目考试时统一结束"),
    OTHER_ERROR("-999", "其他未知错误");

    // 状态码
    private final String code;
    // 状态描述
    private final String message;

    public static A17C55VoEnum findByCode(String code) {
        for (A17C55VoEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return A17C55VoEnum.OTHER_ERROR;
    }
}
