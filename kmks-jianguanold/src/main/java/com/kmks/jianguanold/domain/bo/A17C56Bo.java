package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试科目考试结束
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C56Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 照片
    @NotBlank(message = "照片不能为空")
    private String zp;

    // 结束时间
    @NotBlank(message = "结束时间不能为空")
    private String jssj;

    // 考试成绩
    @NotBlank(message = "考试成绩不能为空")
    private String kscj;

    // 更换考生 默认为0。0：不更换，1：更换。
    @NotBlank(message = "更换考生不能为空")
    private String ghks;


}
