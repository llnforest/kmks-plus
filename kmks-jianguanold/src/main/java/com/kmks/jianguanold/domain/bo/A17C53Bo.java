package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试扣分
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C53Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考试项目
    @NotBlank(message = "考试项目不能为空")
    private String ksxm;

    // 扣分项目
    @NotBlank(message = "扣分项目不能为空")
    private String kfxm;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 扣分时间
    @NotBlank(message = "扣分时间不能为空")
    private String kfsj;

}
