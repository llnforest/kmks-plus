package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考生签到
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17CB2Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考场序号
    @NotBlank(message = "考场序号不能为空")
    private String kcxh;

    // 考试场次
    @NotBlank(message = "考试场次不能为空")
    private String kscc;

    // 签到项目 本次签到所考项目代码的集合，为空时表示签到全部项目，多个项目时，项目代码间以“,”分隔。
    private String qdxm;

}
