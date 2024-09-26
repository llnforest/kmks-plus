package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 读取考生照片信息
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C58Bo {
    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

}
