package com.kmks.w2.domain.vo;


import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 考试过程信息视图对象
 *
 * @author ruoyi
 * @date 2023-05-24
 */
@Data
@ExcelIgnoreUnannotated
public class W2FlowVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 考生编号
     */
    @ExcelProperty(value = "考生编号")
    private String ksbh;

    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    private String ksxm;

    /**
     * 考试状态
     */
    @ExcelProperty(value = "考试状态")
    private String kszt;

    /**
     * 考试时间
     */
    @ExcelProperty(value = "考试时间")
    private Date kssj;

    /**
     * 考试扣分
     */
    @ExcelProperty(value = "考试扣分")
    private Long kskf;

    /**
     * 扣分代码
     */
    @ExcelProperty(value = "扣分代码")
    private String kfdm;

    /**
     * 设备编号
     */
    @ExcelProperty(value = "设备编号")
    private String sbbh;

    /**
     * 考试次数
     */
    @ExcelProperty(value = "考试次数")
    private Long kscs;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String scbj;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String msg;

    /**
     * 考车编号
     */
    @ExcelProperty(value = "考车编号")
    private String kcbh;

    /**
     * 照片
     */
    @ExcelProperty(value = "照片")
    private String zp;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String zpbs;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String zjhm;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String xyh;

    /**
     * 公安代码
     */
    @ExcelProperty(value = "公安代码")
    private String gadm;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String icode;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String imessage;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String lsh;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String xtlb;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String jkid;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String upxml;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String upstatus;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String upret;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String upjpgxml;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String jkjpgid;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Long zpga;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    private String kskm;

    /**
     * 考车号牌
     */
    @ExcelProperty(value = "考车号牌")
    private String kchp;

    /**
     * 考试成绩
     */
    @ExcelProperty(value = "考试成绩")
    private Long kscj;

    /**
     * 考生姓名
     */
    @ExcelProperty(value = "考生姓名")
    private String xm;

    /**
     * 考试员1
     */
    @ExcelProperty(value = "考试员1")
    private String ksy1;

    /**
     * 考试员2
     */
    @ExcelProperty(value = "考试员2")
    private String ksy2;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksysfzhm1;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksysfzhm2;

    /**
     * 添加日期
     */
    @ExcelProperty(value = "添加日期")
    private Date addtime;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String jkUpstatus;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String jkJpgupstatus;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String judgeid;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String xmmc;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Date curtime;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Long iYekao;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Date uptime;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private Long bigzt;

    /**
     * 约考日期
     */
    @ExcelProperty(value = "约考日期")
    private String ykrq;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String flag;



}
