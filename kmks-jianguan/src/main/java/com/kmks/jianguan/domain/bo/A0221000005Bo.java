package com.kmks.jianguan.domain.bo;

import com.kmks.jianguan.validate.CustomGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A0221000005Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空",groups = {CustomGroup.class})
    private String sfzmhm;
}
