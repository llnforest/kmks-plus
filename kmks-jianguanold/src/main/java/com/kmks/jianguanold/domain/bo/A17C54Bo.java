package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试过程图片
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C54Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考试项目
    @NotBlank(message = "考试项目不能为空")
    private String ksxm;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 抓拍时间
    @NotBlank(message = "抓拍时间不能为空")
    private String zpsj;

    // 照片
    @NotBlank(message = "照片不能为空")
    private String zp;

    // 车速
    @NotBlank(message = "车速不能为空")
    private String cs;

}
