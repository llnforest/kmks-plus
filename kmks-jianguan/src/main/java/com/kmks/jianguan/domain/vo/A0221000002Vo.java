package com.kmks.jianguan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
@AllArgsConstructor

public class A0221000002Vo {
    // 考试科目
    private String kskm;
    // 考试原因
    private String ksyy;
    // 预约日期
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime yyrq;
    // 约考日期
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ykrq;
    // 考试车型
    private String kscx;
    // 考场编号
    private String kcbh;
    // 考试场次
    private String kscc;
    // 考试次数
    private Integer kscs;
    // 预约次数
    private Integer yycs ;
    // 本次预约考试次数
    private Integer bcyykscs;
    // 缴款标记
    private String jkbj;
    // 操作类型
    private String czlx;
    // 姓名
    private String xm;
}
