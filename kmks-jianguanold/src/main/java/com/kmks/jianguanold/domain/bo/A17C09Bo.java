package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 时间同步
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C09Bo {
    //科目考试系统序号
    @NotBlank(message = "序号不能为空")
    private String xh;

}
