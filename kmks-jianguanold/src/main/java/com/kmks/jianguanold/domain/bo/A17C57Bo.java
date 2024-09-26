package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询成绩单上需打印照片
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C57Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 考试日期  格式yyyymmdd
    @NotBlank(message = "考试日期不能为空")
    private String ksrq;

    // 预约次数
    @NotBlank(message = "预约次数不能为空")
    private String yycs;

    // 本次预约考试次数
    @NotBlank(message = "本次预约考试次数不能为空")
    private String bcyykscs;


}
