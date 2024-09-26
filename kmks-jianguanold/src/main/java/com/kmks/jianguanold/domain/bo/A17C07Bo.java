package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试分组明细信息下载
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C07Bo {
    //分组序号
    @NotBlank(message = "分组序号不能为空")
    private String xh;

}
