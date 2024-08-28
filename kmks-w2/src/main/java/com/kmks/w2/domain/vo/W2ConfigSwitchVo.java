package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 项目监控视图对象 w2_config_switch
 *
 * @author lynn
 * @date 2023-05-04
 */
@Data
@ExcelIgnoreUnannotated
public class W2ConfigSwitchVo {

    private static final long serialVersionUID = 1L;


    /**
     * 项目代码
     */
    @ExcelProperty(value = "项目代码")
    private String projectcode;

    /**
     * 设备IP
     */
    @ExcelProperty(value = "设备IP")
    private String deviceip;

    /**
     * 设备端口
     */
    @ExcelProperty(value = "设备端口")
    private String deviceport;

    /**
     * 设备名称
     */
    @ExcelProperty(value = "设备名称")
    private String deviceusername;

    /**
     * 设备密码
     */
    @ExcelProperty(value = "设备密码")
    private String devicepassword;

    /**
     * 通道类型
     */
    @ExcelProperty(value = "通道类型")
    private Long channeltype;

    /**
     * 远程设备通道号
     */
    @ExcelProperty(value = "远程设备通道号")
    private Long devicechannel;

    /**
     * 传输协议
     */
    @ExcelProperty(value = "传输协议")
    private Long protocol;

    /**
     * 厂商类型
     */
    @ExcelProperty(value = "厂商类型")
    private Long vendortype;

    /**
     * 码流类型
     */
    @ExcelProperty(value = "码流类型")
    private Long bitstreamtype;


}
