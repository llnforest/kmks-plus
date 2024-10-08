package com.kmks.w2.domain.gateDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 图片
 *
 * @author Star
 * @description: TODO
 * @date 2024/5/30 14:12
 */
@Data
public class ImgDataBo {
    /**
     * 文本内容
     **/
    @NotBlank(message = "图片地址不能为空")
    String url;

}
