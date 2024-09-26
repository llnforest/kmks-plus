package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试误判申请 (未申请)
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17CB4Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 误判问题描述
    @NotBlank(message = "误判问题描述不能为空")
    private String wpwtms;

    // 申请内容
    @NotBlank(message = "申请内容不能为空")
    private String sqnr;

}
