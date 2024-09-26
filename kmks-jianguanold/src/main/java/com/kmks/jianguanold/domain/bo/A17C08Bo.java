package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试预约信息下载
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C08Bo {
    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    //考试日期
    @NotBlank(message = "考试日期不能为空")
    private String ksrq;

    //考试地点
    private String ksdd;

    // 身份证明号码
    private String sfzmhm;

}
