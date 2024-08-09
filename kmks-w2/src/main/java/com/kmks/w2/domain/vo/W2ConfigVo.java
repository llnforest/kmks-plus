package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 驾考参数视图对象 w2_config
 *
 * @author ruoyi
 * @date 2023-03-01
 */
@Data
@ExcelIgnoreUnannotated
public class W2ConfigVo {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @ExcelProperty(value = "编码")
    private Long lIncode;

    /**
     * 标志参数
     */
    @ExcelProperty(value = "标志参数")
    private Long iFlag;

    /**
     * 标志类型
     */
    @ExcelProperty(value = "标志类型")
    private String sFlag;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String sName;

    /**
     * 标志值
     */
    @ExcelProperty(value = "标志值")
    private Long iValue;

    /**
     * 标志值
     */
    @ExcelProperty(value = "标志值")
    private String sValue;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String sBeizhu;


}
