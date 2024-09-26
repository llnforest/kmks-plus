package com.kmks.jianguanold.domain.bo;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

/**
 * 考试项目结束
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C55Bo {
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

    private String sbxh;

    // 结束时间
    @NotBlank(message = "结束时间不能为空")
    private String jssj;

    // 操作类型 1:正常，0：撤销该考试项目记录
    @NotBlank(message = "操作类型不能为空")
    private String czlx;

    @AssertTrue(message = "设备序号不能为空")
    public boolean isValidateSbxh(){
        return !(kskm.equals("2") && ObjectUtil.isEmpty(sbxh));
    }

}
