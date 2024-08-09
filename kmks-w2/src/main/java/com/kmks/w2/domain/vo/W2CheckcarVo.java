package com.kmks.w2.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车辆自检视图对象 w2_checkcar
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2CheckcarVo {

    private static final long serialVersionUID = 1L;

    /**
     * 车辆编号
     */
    @ExcelProperty(value = "车辆编号")
    private String sCarno;

    /**
     * 考车自检时间
     */
    @ExcelProperty(value = "考车自检时间")
    private String sTime;

    /**
     * 考车自检状态
     */
    @ExcelProperty(value = "考车自检状态")
    private String sValue;

    /**
     * 考车自检说明
     */
    @ExcelProperty(value = "考车自检说明")
    private String sNote;

    /**
     * 自检日期
     */
    @ExcelProperty(value = "自检日期")
    private Date dtFs;


}
