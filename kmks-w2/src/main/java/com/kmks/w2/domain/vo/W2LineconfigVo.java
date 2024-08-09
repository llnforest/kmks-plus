package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 路线管理视图对象 w2_lineconfig
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2LineconfigVo {

    private static final long serialVersionUID = 1L;

    /**
     * 路线
     */
    @ExcelProperty(value = "路线")
    private Long line;

    /**
     * 监管代码
     */
    @ExcelProperty(value = "监管代码")
    private String linecode;

    /**
     * 线路状态
     */
    @ExcelProperty(value = "线路状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "line_status")
    private Long linezt;


}
