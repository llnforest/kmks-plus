package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车辆信息视图对象 w2_carinfo
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2CarinfoVo {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private String xh;

    /**
     * 号牌类型
     */
    @ExcelProperty(value = "号牌类型")
    private String hpzl;

    /**
     * 号牌号码
     */
    @ExcelProperty(value = "号牌号码")
    private String hphm;

    /**
     * 准驾车型
     */
    @ExcelProperty(value = "准驾车型")
    private String syzjcx;

    /**
     * 车辆类型
     */
    @ExcelProperty(value = "车辆类型")
    private String cllx;

    /**
     * 车辆品牌
     */
    @ExcelProperty(value = "车辆品牌")
    private String clpp;

    /**
     * 初次登记日期
     */
    @ExcelProperty(value = "初次登记日期")
    private String ccdjrq;

    /**
     * 强制报废日期
     */
    @ExcelProperty(value = "强制报废日期")
    private String qzbfqz;

    /**
     * 发证机关
     */
    @ExcelProperty(value = "发证机关")
    private String fzjg;

    /**
     * 车辆状态
     */
    @ExcelProperty(value = "车辆状态")
    private String zt;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksczt;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String shr;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ipdz;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String gkjmac;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String gkjbh;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String gkjdk;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String cczdip;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String cczdmac;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String cczdbh;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String cczddk;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String sskcxh;


}
