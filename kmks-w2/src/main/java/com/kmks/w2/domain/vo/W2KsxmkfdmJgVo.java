package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 扣分代码视图对象 w2_ksxmkfdm_jg
 *
 * @author lynn
 * @date 2024-04-23
 */
@Data
@ExcelIgnoreUnannotated
public class W2KsxmkfdmJgVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 项目代码
     */
    @ExcelProperty(value = "项目代码")
    private Long ksxmdm;

    /**
     * 扣分代码
     */
    @ExcelProperty(value = "扣分代码")
    private String gakfdm;

    /**
     * 扣分类别
     */
    @ExcelProperty(value = "扣分类别")
    private String gakfmc;

    /**
     * 扣分说明
     */
    @ExcelProperty(value = "扣分说明")
    private String kfmc;

    /**
     * 扣分值
     */
    @ExcelProperty(value = "扣分值")
    private Long kf;

    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    private String ksxm;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_kskm")
    private String kskm;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String sBeizhu2;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String ksxmxh;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private Long flag;


}
