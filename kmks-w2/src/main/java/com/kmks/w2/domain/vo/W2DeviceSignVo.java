package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 签到设备视图对象 w2_device_sign
 *
 * @author lynn
 * @date 2024-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class W2DeviceSignVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long signId;

    /**
     * 签到设备
     */
    @ExcelProperty(value = "签到设备")
    private String signName;

    /**
     * 设备IP
     */
    @ExcelProperty(value = "设备IP")
    private String signIp;

    /**
     * 设备MAC
     */
    @ExcelProperty(value = "设备MAC")
    private String signMac;

    /**
     * 设备备注
     */
    @ExcelProperty(value = "设备备注")
    private String remark;

    /**
     * 设备状态
     */
    @ExcelProperty(value = "设备状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_open_status")
    private String signStatus;


}
