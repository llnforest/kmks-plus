package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 考试场地备案信息下载
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C01Bo {
    //发证机关
    @NotBlank(message = "发证机关不能为空")
    private String fzjg;

    //考试科目
    private String kskm;

    //更新时间
    private String gxsj;

}
