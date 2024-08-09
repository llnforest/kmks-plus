package com.kmks.w2.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;



/**
 * 考车自检视图对象 w2_kcxx_check
 *
 * @author Lynn
 * @date 2024-06-19
 */
@Data
@ExcelIgnoreUnannotated
public class W2KcxxCheckVo {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 考车编号
     */
    @ExcelProperty(value = "考车编号")
    private String kcbh;

    /**
     * 自检内容
     */
    @ExcelProperty(value = "自检内容")
    private String checkContent;

    /**
     * 自检结果
     */
    @ExcelProperty(value = "自检结果", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "check_result")
    private Long checkResult;



    /**
     * 自检时间
     */
    @ExcelProperty(value = "自检时间")
    private Date checkTime;


}
