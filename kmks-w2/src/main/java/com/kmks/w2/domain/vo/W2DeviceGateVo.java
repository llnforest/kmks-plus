package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 闸机设备视图对象 w2_device_gate
 *
 * @author Lynn
 * @date 2024-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class W2DeviceGateVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long gateId;

    /**
     * 闸机设备
     */
    @ExcelProperty(value = "闸机设备")
    private String gateName;

    /**
     * 闸机IP
     */
    @ExcelProperty(value = "设备IP")
    private String gateIp;

    /**
     * 闸机MAC
     */
    @ExcelProperty(value = "设备MAC")
    private String gateMac;

    /**
     * 设备备注
     */
    @ExcelProperty(value = "设备备注")
    private String remark;

    /**
     * 闸机状态
     */
    @ExcelProperty(value = "设备状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_open_status")
    private String gateStatus;


}
