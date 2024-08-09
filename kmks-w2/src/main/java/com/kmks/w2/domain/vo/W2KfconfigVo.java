package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

/**
 * 评判参数视图对象 w2_kfconfig
 *
 * @author Lynn
 * @date 2023-03-21
 */
@Data
@ExcelIgnoreUnannotated
public class W2KfconfigVo {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @ExcelProperty(value = "唯一标识")
    @Column(exists = {"0","1","2","3","4","5","6","7","8","9"})
    private String gakey;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    @Column(exists = {"0","1","2","3","4","5","6","7","8","9"})
    private String kskm;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    @Column(exists = {"0","1","2","3","4","5","6","7","8","9"})
    private String gakfmc;

    /**
     * 项目序号
     */
    @ExcelProperty(value = "项目序号")
    private Long xmxh;

    /**
     * 项目扣分说明
     */
    @ExcelProperty(value = "项目扣分说明")
    @Column(exists = {"0","1","2","3","4","5","6","7","8","9"})
    private String kfmc;

    /**
     * 参数值
     */
    @ExcelProperty(value = "参数值")
    @Column(exists = {"0","3","4","5","8","9"})
    private String value;

    /**
     * 参数说明
     */
    @ExcelProperty(value = "参数说明")
    @Column(exists = {"0","3","4","5","8","9"})
    private String beizhu;



    /**
     * 扣分代码
     */
    @ExcelProperty(value = "扣分代码")
    private String gakfdm;

    /**
     * 评判方式
     */
    @ExcelProperty(value = "评判方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "param_judge_type")
    @Column(exists = {"2","7"})
    private String autoflag;

    /**
     * 考试项目
     */
//    @ExcelProperty(value = "考试项目")
    private String ksxm;

    /**
     * 项目代码
     */
//    @ExcelProperty(value = "项目代码")
    private Long ksxmdm;

    /**
     * 扣分值
     */
    @ExcelProperty(value = "扣分值")
    @Column(exists = {"1","2","3","4","6","7","8","9"})
    private Long kf;

    /**
     * $column.columnComment
     */
//    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String id;

    /**
     * 分类标记
     */
//    @ExcelProperty(value = "分类标记")
    private String paramtype;

    /**
     * $column.columnComment
     */
//    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String autoflag1;

    /**
     * $column.columnComment
     */
//    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String xmtype;


}
