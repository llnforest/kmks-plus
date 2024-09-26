package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 身份信息比对
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C51Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考试系统编号
    @NotBlank(message = "考试系统编号不能为空")
    private String ksxtbh;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 考试员证件号码，摩托车时不能为空
    private String ksysfzmhm;

    // 照片
    @NotBlank(message = "照片不能为空")
    private String zp;

    // 开始时间
    @NotBlank(message = "开始时间不能为空")
    private String kssj;

    // 考试员2身份证明号码
    private String Ksy2sfzmhm;

    // 监管分车必传
    private String kchp;

    // 监管分车，科三必传
    private String ksxl;
}
