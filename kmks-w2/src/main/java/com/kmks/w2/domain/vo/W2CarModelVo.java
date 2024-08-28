package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车辆模型视图对象 w2_carmodel
 *
 * @author ghgd
 * @date 2023-03-14
 */
@Data
@ExcelIgnoreUnannotated
public class W2CarModelVo {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 车模名称
     */
    @ExcelProperty(value = "车模名称")
    private String modelname;

    /**
     * 车模类型
     */
    @ExcelProperty(value = "车模类型")
    private String modeltype;

    /**
     * 车模点位树
     */
    @ExcelProperty(value = "车模点位树")
    private String pointdata;

    /**
     * 状态
     */
    private String state;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String state1;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "${comment}", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "$column.readConverterExp()")
    private String scode;

    /**
     * 车模导入点位数
     */
    @ExcelProperty(value = "车模导入点位数")
    private String temppointdata;

    /**
     * 车牌号
     */
    @ExcelProperty(value = "车牌号")
    private String cph;

    /**
     * 车模标记
     */
    private Long relationId;

}
