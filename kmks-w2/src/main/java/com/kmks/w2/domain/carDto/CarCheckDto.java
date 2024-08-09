package com.kmks.w2.domain.carDto;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 车辆信息校验
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
public class CarCheckDto {
    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空")
    private String kch;

    /**
     * 车牌号码
     */
    @NotBlank(message = "车牌号码不能为空")
    private String cph;

    /**
     * 考车IP
     */
    @NotBlank(message = "考车IP不能为空")
    private String carIp;

    /**
     * Mac地址
     */
    @NotBlank(message = "Mac地址不能为空")
    private String carMac;

    /**
     * 软件版本号
     */
    @NotBlank(message = "软件版本号不能为空")
    private String carVersion;

}
