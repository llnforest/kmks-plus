package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车辆信号视图对象 w2_carsignal
 *
 * @author Lynn
 * @date 2024-05-29
 */
@Data
@ExcelIgnoreUnannotated
public class W2CarsignalVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 信号标识
     */
    @ExcelProperty(value = "信号标识")
    private String signalKey;

    /**
     * 信号数值
     */
    @ExcelProperty(value = "信号数值")
    private String signalValue;

    /**
     * 信号名称
     */
    @ExcelProperty(value = "信号名称")
    private String signalName;

    /**
     * 信号自检
     */
    @ExcelProperty(value = "信号自检", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_status")
    private String signalNote;


}
