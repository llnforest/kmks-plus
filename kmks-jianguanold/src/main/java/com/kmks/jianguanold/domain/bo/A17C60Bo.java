package com.kmks.jianguanold.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 暂停考生考试 (未申请)
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A17C60Bo {
    //考试科目
    @NotBlank(message = "考试科目不能为空")
    private String kskm;

    // 考试日期  格式yyyy-mm-dd
    @NotBlank(message = "考试日期不能为空")
    private String ksrq;

    // 考场序号
    @NotBlank(message = "考场序号不能为空")
    private String kcxh;

    // 身份证明号码
    @NotBlank(message = "身份证明号码不能为空")
    private String sfzmhm;

    // 取消/暂停类型  1暂停考试；2设备异常重考；3成绩不合格； 4：考试恢复
    @NotBlank(message = "取消/暂停类型不能为空")
    private String qxlx;

    // 取消/暂停原因
    private String ztyy;


}
