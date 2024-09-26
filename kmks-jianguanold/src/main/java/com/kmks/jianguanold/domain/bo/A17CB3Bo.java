package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 随机安排考试
 *
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17CB3Bo {
    //考车号牌
    @NotBlank(message = "考车号牌不能为空")
    private String kchp;

    // 考场序号
    @NotBlank(message = "考场序号不能为空")
    private String kcxh;

    // 考试场次
    @NotBlank(message = "考试场次不能为空")
    private String kscc;

    // 签到项目 本次签到所考项目代码的集合，为空时表示签到全部项目，多个项目时，项目代码间以“,”分隔。
    private String qdxm;

    //考试员1身份证明号码
    private String ksysfzmhm;

    //考试员2身份证明号码
    private String ksy2sfzmhm;

    //随机人数 考车申请考试监管系统随机安排的考生人数。考试监管系统对不同科目设定单辆考车一次允许随机分配考生的最大值，如接口输入的参数超过系统设定参数的最大值，按照系统设定参数最大值进行随机分配。
    private String sjrs;

}
