package com.kmks.jianguanold.domain.bo;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

/**
 * 考试项目开始
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C52Bo {
    //流水号
    @NotBlank(message = "流水号不能为空")
    private String lsh;

    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 考试项目
    @NotBlank(message = "考试项目不能为空")
    private String ksxm;

    // 设备序号
    private String sbxh;

    // 考车号牌
    private String kchp;

    // 开始时间
    @NotBlank(message = "开始时间不能为空")
    private String kssj;

    // 监管分车，科三必传
    private String ksxl;

    @AssertTrue(message = "设备序号不能为空")
    public boolean isValidateSbxh(){
        return !(kskm.equals("2") && ObjectUtil.isEmpty(sbxh));
    }

    @AssertTrue(message = "考车号牌不能为空")
    public boolean isValidateKchp(){
        return !(kskm.equals("3") && ObjectUtil.isEmpty(kchp));
    }

}
