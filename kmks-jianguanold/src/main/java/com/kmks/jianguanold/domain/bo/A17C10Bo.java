package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询考生成绩单签字信息
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C10Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

}
