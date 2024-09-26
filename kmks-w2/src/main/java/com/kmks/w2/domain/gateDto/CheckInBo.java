package com.kmks.w2.domain.gateDto;

import com.ruoyi.common.core.validate.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 报到
 *
 * @author Star
 * @description: TODO
 * @date 2024/5/30 14:12
 */
@Data
public class CheckInBo {
    /**
     * 姓名
    **/
    @NotBlank(message = "姓名不能为空", groups = { AddGroup.class})
    String xm;

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

    /**
     * 鉴别照片
     **/
    @NotBlank(message = "鉴别照片不能为空", groups = { AddGroup.class})
    String jbzp;

    String kskm;

}
