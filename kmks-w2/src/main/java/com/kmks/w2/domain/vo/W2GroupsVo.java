package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 明细分组信息视图对象 w2_groups
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2GroupsVo {

    private static final long serialVersionUID = 1L;

    /**
     * 分组序号
     */
    @ExcelProperty(value = "分组序号")
    private String xh;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String xm;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String zjhm;

    /**
     * 考试地点
     */
    @ExcelProperty(value = "考试地点")
    private String ksdd;

    /**
     * 考试场次
     */
    @ExcelProperty(value = "考试场次")
    private String kscc;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    private String kskm;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型")
    private String kscx;

    /**
     * 考试员
     */
    @ExcelProperty(value = "考试员")
    private String ksy;



    /**
     * 考试日期
     */
    @ExcelProperty(value = "考试日期")
    private String ksrq;

    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    private String ksxm;

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
    private String kchp;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String dlr;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String djrq;

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
    private String zkzh;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksyy;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksyybh;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String sfyk;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String yyrq;


}
