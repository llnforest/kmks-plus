package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试计划分组信息下载
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C06Bo {
    //管理部门
    @NotBlank(message = "考管理部门不能为空")
    private String glbm;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;


    //考试日期
    @NotBlank(message = "考试日期不能为空")
    private String ksrq;

    //考场序号
    @NotBlank(message = "考场序号不能为空")
    private String kcxh;

    //考试车型
    private String kscx;

    //考试场次
    private String kscc;

}
