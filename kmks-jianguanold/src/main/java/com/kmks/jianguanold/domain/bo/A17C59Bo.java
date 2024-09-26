package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 暂停考场考试 (未申请)
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C59Bo {
    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考试日期  格式yyyy-mm-dd
    @NotBlank(message = "考试日期不能为空")
    private String ksrq;

    // 考场序号
    @NotBlank(message = "考场序号不能为空")
    private String kcxh;

    // 考场状态 1：正常，2：暂停
    @NotBlank(message = "考场状态不能为空")
    private String kczt;

    // 异常类型  5-考场无视频信号、6-考试车无视频信号、7-遮挡摄像头、8-考试舞弊
    @NotBlank(message = "取消/暂停类型不能为空")
    private String yclx;

    // 取消/暂停原因
    private String ztyy;


}
