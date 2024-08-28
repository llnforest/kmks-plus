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
public class EncryptBo {
    /**
     * 文本内容
    **/
    @NotBlank(message = "文本内容不能为空")
    String text;

}
