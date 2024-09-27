package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试员备案信息同步
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C04Bo {
    //发证机关
    @NotBlank(message = "发证机关不能为空")
    private String fzjg;

    //管理部门
    private String glbm;

    //更新时间
    private String gxsj;

}
