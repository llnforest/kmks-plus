package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备合码器视图对象 w2_config_device
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@ExcelIgnoreUnannotated
public class W2ConfigDeviceVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;
    /**
     * 设备号
     */
    @ExcelProperty(value = "设备号")
    private Long deviceno;

    /**
     * 设备IP地址
     */
    @ExcelProperty(value = "设备IP地址")
    private String deviceip;

    /**
     * 合码器端口号
     */
    @ExcelProperty(value = "合码器端口号")
    private String deviceport;

    /**
     * 合码器用户名
     */
    @ExcelProperty(value = "合码器用户名")
    private String devicename;

    /**
     * 合码器密码
     */
    @ExcelProperty(value = "合码器密码")
    private String devicepassword;


}
