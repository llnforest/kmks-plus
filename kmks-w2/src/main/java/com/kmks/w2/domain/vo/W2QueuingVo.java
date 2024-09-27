package com.kmks.w2.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 排队信息视图对象 w2_queuing
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2QueuingVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 考试编号
     */
    private String ksbh;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String zjhm;

    /**
     * 考生姓名
     */
    @ExcelProperty(value = "考生姓名")
    private String xm;

    /**
     * 排队序号
     */
    @ExcelProperty(value = "排队序号")
    private Long bdxh;

    /**
     * 驾校名称
     */
    private String jxmc;

    /**
     * 缴费状态
     */
    @ExcelProperty(value = "缴费状态")
    private String sqks;

    /**
     * 签到状态
     */
    @ExcelProperty(value = "签到状态")
    private Long sign;

    /**
     * 分车状态
     */
    @ExcelProperty(value = "分车状态")
    private String zt;

    /**
     * 考车编号
     */
    @ExcelProperty(value = "考车编号")
    private String kcbh;

    /**
     * 车牌号码
     */
    @ExcelProperty(value = "车牌号码")
    private String kchp;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型")
    private String kscx;

    /**
     * 线路
     */
    @ExcelProperty(value = "线路")
    private Long rLine;

    /**
     * 锁定状态
     */
//    @ExcelProperty(value = "锁定状态", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(dictType = "queue_is_lock")
    private Long iLock;

    /**
     * 夜考
     */
    @ExcelProperty(value = "夜考")
    private String sfyk;


    /**
     * 考试状态
     */
    @ExcelProperty(value = "考试状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "queue_status")
    private String kszt;

    /**
     * 第几次
     */
    @ExcelProperty(value = "第几次", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "queue_djc")
    private Long djc;

    /**
     * 成绩
     */
    @ExcelProperty(value = "成绩")
    private Long score;


    /**
     * 分配项目
     */
    @ExcelProperty(value = "分配项目")
    private String ksxm;

    /**
     * 完成项目
     */
    @ExcelProperty(value = "完成项目")
    private String wcxm;


    /**
     * 考试员姓名
     */
    private String kgname;

    /**
     * 考试员证件
     */
    private String kg;

    /**
     * 安全员
     */
    private String sSafe;

    /**
     * 安全员证件
     */
    private String sSafeZjhm;

    /**
     * $column.columnComment
     */
    private String zkzh;

    /**
     * $column.columnComment
     */
    private String ykxms;

    /**
     * $column.columnComment
     */
    private String zkxms;


    /**
     * $column.columnComment
     */
    private Long kscs;

    /**
     * $column.columnComment
     */
    private Long ykcs;

    /**
     * $column.columnComment
     */
    private String wcxms;

    /**
     * $column.columnComment
     */
    private Long kscj;

    /**
     * $column.columnComment
     */
    private Date kssj;

    /**
     * $column.columnComment
     */
    private String cdxmbh;

    /**
     * $column.columnComment
     */
    private String sfyz;

    /**
     * 日考夜考是否分开 默认可以一起考
     */
//    @ExcelProperty(value = "日考夜考是否分开 默认可以一起考")
    private String kslx;

    /**
     * $column.columnComment
     */
    private String jxdm;

    /**
     * 考试日期
     */
    @ExcelProperty(value = "考试日期")
    private Date ksrq;

    /**
     * $column.columnComment
     */
    private Date sqsj;

    /**
     * $column.columnComment
     */
    private String qxlx;

    /**
     * $column.columnComment
     */
    private String kcxh;

    /**
     * $column.columnComment
     */
    private String flag;

    /**
     * $column.columnComment
     */
    private String jszt;

    /**
     * $column.columnComment
     */
    private String lsh;

    /**
     * $column.columnComment
     */
    private String zkxmdm;

    /**
     * 指纹
     */
    private String finger;

    /**
     * $column.columnComment
     */
    private String zsfhg;

    /**
     * $column.columnComment
     */
    private Long zcs;

    /**
     * $column.columnComment
     */
    private String kfxm;

    /**
     * $column.columnComment
     */
    private String kcbs;

    /**
     * $column.columnComment
     */
    private String fieldid;

    /**
     * $column.columnComment
     */
    private String kg2;

    /**
     * 考试成绩
     */
    private Long kscc;

    /**
     * $column.columnComment
     */
    private String signcontent;

    /**
     * $column.columnComment
     */
    private String ksyy;

    /**
     * $column.columnComment
     */
    private String jbr;

    /**
     * $column.columnComment
     */
    private String glbm;

    /**
     * $column.columnComment
     */
    private String yycs;

    /**
     * $column.columnComment
     */
    private String bcyykscs;

    /**
     * $column.columnComment
     */
    private String linecode;

    /**
     * $column.columnComment
     */
    private Date xmkssj;

    /**
     * $column.columnComment
     */
    private String message;

    /**
     * $column.columnComment
     */
    private Long iVoiceTimes;

    /**
     * $column.columnComment
     */
    private Date dVoiceDate;

    /**
     * $column.columnComment
     */
    private Long iVoiceReady;

    /**
     * $column.columnComment
     */
    private Long cwcs;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "考试科目")
    private String kskm;

    private Long totalNum;

}
