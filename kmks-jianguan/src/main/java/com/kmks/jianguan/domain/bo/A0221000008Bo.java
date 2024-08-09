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
public class A0221000008Bo {
    //考车号牌
    @NotBlank(message = "考车号牌不能为空",groups = {CustomGroup.class})
    private String kchp;

    //考试场次
    private String kscc;

    //签到项目
    private String qdxm;

    // 随机人数(科目三非空)
    private String sjrs;
}
