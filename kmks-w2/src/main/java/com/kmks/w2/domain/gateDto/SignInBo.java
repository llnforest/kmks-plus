package com.kmks.w2.domain.gateDto;

import com.ruoyi.common.core.validate.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 签到Bo
 *
 * @author Star
 * @description: TODO
 * @date 2024/5/30 14:12
 */
@Data
public class SignInBo {
    /**
     * 证件号码
     **/
    @NotBlank(message = "证件号码不能为空", groups = { AddGroup.class})
    String zjhm;

    /**
     * 证件照片
     **/
    @NotBlank(message = "证件照片不能为空", groups = { AddGroup.class})
    String zjzp;

//    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class})
    String kskm;
}
