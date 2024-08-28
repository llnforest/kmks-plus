package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车辆合码器视图对象 w2_config_car
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@ExcelIgnoreUnannotated
public class W2ConfigCarVo {

    private static final long serialVersionUID = 1L;


    /**
     * 车号
     */
    @ExcelProperty(value = "车号")
    private String carno;

    /**
     * 合码器设备号
     */
    @ExcelProperty(value = "合码器设备号")
    private Long deviceno;

    /**
     * 合码器输出口
     */
    @ExcelProperty(value = "合码器输出口")
    private Long deviceoutputno;

    /**
     * 解码通道
     */
    @ExcelProperty(value = "解码通道")
    private Long videochannel;


}
