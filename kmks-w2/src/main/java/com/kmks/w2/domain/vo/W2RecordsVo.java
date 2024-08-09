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
 * 成绩管理视图对象 w2_records
 *
 * @author lynn
 * @date 2023-04-10
 */
@Data
@ExcelIgnoreUnannotated
public class W2RecordsVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

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
     * 考车编号
     */
    @ExcelProperty(value = "考车编号")
    private String kcbh;

    /**
     * 准考证号
     */
//    @ExcelProperty(value = "准考证号")
    private String zkzh;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "param_car_type")
    private String kscx;

    /**
     * 考试原因
     */
    @ExcelProperty(value = "考试原因", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "record_ksyy")
    private String ksyy;

    /**
     * 结果
     */
    @ExcelProperty(value = "结果", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "record_ksjg")
    private String ksjg;

    /**
     * 预约次数
     */
    @ExcelProperty(value = "预约次数")
    private Long yycs;

    /**
     * 考试日期1
     */
    @ExcelProperty(value = "考试日期1")
    private Date ksrq1;

    /**
     * 开始时间1
     */
    @ExcelProperty(value = "开始时间1")
    private Date kssj1;

    /**
     * 结束时间1
     */
    @ExcelProperty(value = "结束时间1")
    private Date jssj1;

    /**
     * 扣分信息1
     */
    @ExcelProperty(value = "扣分信息1")
    private String kfxx1;

    /**
     * 分数1
     */
    @ExcelProperty(value = "分数1")
    private Long jgfs1;

    /**
     * 考官1
     */
//    @ExcelProperty(value = "考官1")
    private String ksy1;

    /**
     * 考试日期2
     */
    @ExcelProperty(value = "考试日期2")
    private Date ksrq2;

    /**
     * 开始时间2
     */
    @ExcelProperty(value = "开始时间2")
    private Date kssj2;

    /**
     * 结束时间2
     */
    @ExcelProperty(value = "结束时间2")
    private Date jssj2;

    /**
     * 扣分信息2
     */
    @ExcelProperty(value = "扣分信息2")
    private String kfxx2;

    /**
     * 分数2
     */
    @ExcelProperty(value = "分数2")
    private Long jgfs2;

    /**
     * 考官2
     */
//    @ExcelProperty(value = "考官2")
    private String ksy2;

    /**
     * 考试次数
     */
    @ExcelProperty(value = "考试次数")
    private Long kscs;

    /**
     * 效验码
     */
//    @ExcelProperty(value = "效验码")
    private String sjjyw;

    /**
     * 打印
     */
//    @ExcelProperty(value = "打印", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(dictType = "record_is_print")
    private String sfprint;



    /**
     * 路线
     */
    @ExcelProperty(value = "路线")
    private Long line;

    /**
     * 驾校名称
     */
//    @ExcelProperty(value = "驾校名称")
    private String jxmc;

    /**
     * $column.columnComment
     */
    private String ksbh;

    /**
     * $column.columnComment
     */
    private String kscj1;

    /**
     * $column.columnComment
     */
    private String scf1;

    /**
     * $column.columnComment
     */
    private String kscj2;

    /**
     * $column.columnComment
     */
    private String scf2;

    /**
     * $column.columnComment
     */
    private String kscc;

    /**
     * $column.columnComment
     */
    private String czy;

    /**
     * $column.columnComment
     */
    private String zt;

    /**
     * $column.columnComment
     */
    private String ksxm;

    /**
     * $column.columnComment
     */
    private String sqbs;

    /**
     * $column.columnComment
     */
    private Date sqrq;

    /**
     * $column.columnComment
     */
    private String sqyy;

    /**
     * $column.columnComment
     */
    private String sqr;

    /**
     * $column.columnComment
     */
    private String spr;

    /**
     * $column.columnComment
     */
    private Date sprq;

    /**
     * $column.columnComment
     */
    private String sqr1;

    /**
     * $column.columnComment
     */
    private String sfqk;

    /**
     * $column.columnComment
     */
    private String zjzp;

    /**
     * $column.columnComment
     */
    private String jbzp;

    /**
     * $column.columnComment
     */
    private Long dycs;

    /**
     * $column.columnComment
     */
    private String flag;

    /**
     * $column.columnComment
     */
    private String kcxh;

    /**
     * $column.columnComment
     */
    private Date ksrq;

    /**
     * $column.columnComment
     */
    private String kfss1;

    /**
     * $column.columnComment
     */
    private String kfss2;

    /**
     * $column.columnComment
     */
    private String sszp;

    /**
     * $column.columnComment
     */
    private String sSafe;

    /**
     * $column.columnComment
     */
    private String sSafeZjhm;

    /**
     * 驾校代码
     */
//    @ExcelProperty(value = "驾校代码")
    private String jxdm;

    /**
     * $column.columnComment
     */
    private String lsh;

    /**
     * $column.columnComment
     */
    private String kchp;

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
    private String bcyykscs;

    /**
     * $column.columnComment
     */
    private String jkSszpstatus;

    /**
     * $column.columnComment
     */
    private String sfyk;

    /**
     * $column.columnComment
     */
    private String sZjmc;

    /**
     * $column.columnComment
     */
    private String sXmhz;

    /**
     * $column.columnComment
     */
    private String ykrq;

    /**
     * $column.columnComment
     */
    private Long ykflag;

    /**
     * $column.columnComment
     */
    private Long ykdjc;

    /**
     * $column.columnComment
     */
    private Long line2;

    /**
     * $column.columnComment
     */
    private String sCity;

    /**
     * $column.columnComment
     */
    private Long iDiff;

    /**
     * $column.columnComment
     */
    private String dcrk;

    /**
     * $column.columnComment
     */
    private String ksxmdown;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    private String kskm;

    /**
     * $column.columnComment
     */
    private String hlSignUpload;

    /**
     * $column.columnComment
     */
    private String cjupload;

    /**
     * $column.columnComment
     */
    private String autojudge1;

    /**
     * $column.columnComment
     */
    private String autojudge2;

    /**
     * $column.columnComment
     */
    private String kszt;

    private int totalNum;


}
