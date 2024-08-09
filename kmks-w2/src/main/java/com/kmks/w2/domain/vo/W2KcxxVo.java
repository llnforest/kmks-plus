package com.kmks.w2.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 过程明细视图对象 w2_kcxx
 *
 * @author lynn
 * @date 2023-03-14
 */
@Data
@ExcelIgnoreUnannotated
public class W2KcxxVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 考车序号
     */
    @ExcelProperty(value = "考车序号")
    private String kch;

    /**
     * 考车名称
     */
    @ExcelProperty(value = "考车名称")
    private String cph;

    /**
     * 车型名称
     */
    @ExcelProperty(value = "车型名称")
    private String kcmc;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "param_car_type")
    private String kscx;

    /**
     * 考车状态
     */
    @ExcelProperty(value = "考车状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "param_car_status")
    private String zt;

    /**
     * 项目序号
     */
    @ExcelProperty(value = "项目序号")
    private String xmxh;

    /**
     * 考试路线
     */
    private Long line;

    /**
     * 车载视频IP
     */
    @ExcelProperty(value = "车载视频IP")
    private String czip;

    /**
     * 车载用户
     */
    @ExcelProperty(value = "车载用户")
    private String cuser;

    /**
     * 车载密码
     */
    @ExcelProperty(value = "车载密码")
    private String cpwd;

    /**
     * 中心视频IP
     */
    @ExcelProperty(value = "中心视频IP")
    private String zxip;

    /**
     * 中心视频用户
     */
    @ExcelProperty(value = "中心视频用户")
    private String zuser;

    /**
     * 中心视频密码
     */
    @ExcelProperty(value = "中心视频密码")
    private String zpwd;

    /**
     * 四合一视频IP
     */
    @ExcelProperty(value = "四合一视频IP")
    private String fourip;

    /**
     * 四合一视频用户
     */
    @ExcelProperty(value = "四合一视频用户")
    private String fouruser;

    /**
     * 四合一视频密码
     */
    @ExcelProperty(value = "四合一视频密码")
    private String fourpwd;

    /**
     * 车辆ip
     */
    @ExcelProperty(value = "车辆ip")
    private String carIp;

    /**
     * 车辆mac
     */
    @ExcelProperty(value = "车辆mac")
    private String carMac;

    /**
     * 软件版本
     */
    private String carVersion;



    /**
     * 考场Id
     */
    private String schoolId;

    /**
     * 考试编号
     */
    private String ksbh;

    /**
     * $column.columnComment
     */
    private String mdzt;

    /**
     * $column.columnComment
     */
    private Long mdrs;

    /**
     * $column.columnComment
     */
    private String zc;

    /**
     * 'N':默认,'Y':进行,'Z':终止。
     */
    @ExcelProperty(value = "'N':默认,'Y':进行,'Z':终止。")
    private String lxbs;

    /**
     * $column.columnComment
     */
    private String msg;

    /**
     * $column.columnComment
     */
    private String fieldid;

    /**
     * $column.columnComment
     */
    private String fieldname;

    /**
     * $column.columnComment
     */
    private Long fieldstatus;

    /**
     * $column.columnComment
     */
    private String signcheck;

    /**
     * $column.columnComment
     */
    private String allline;

    /**
     * $column.columnComment
     */
    private String curline;

    /**
     * $column.columnComment
     */
    private String xm;

    /**
     * $column.columnComment
     */
    private String zkxms;

    /**
     * $column.columnComment
     */
    private String jxmc;

    /**
     * $column.columnComment
     */
    private Long djc;

    /**
     * $column.columnComment
     */
    private Long rLine;

    /**
     * $column.columnComment
     */
    private String kgname;

    /**
     * $column.columnComment
     */
    private String sSafe;

    /**
     * $column.columnComment
     */
    private String message;

    /**
     * $column.columnComment
     */
    private String ipadddress;

    /**
     * $column.columnComment
     */
    private String macaddress;

    /**
     * $column.columnComment
     */
    private Long checkResult1;

    /**
     * $column.columnComment
     */
    private Long checkResult2;

    /**
     * $column.columnComment
     */
    private Long checkResult3;

    /**
     * 车载摄像头1地址
     */
    @ExcelProperty(value = "车载摄像头1地址")
    private String carCamera1Ip;

    /**
     * 车载摄像头2地址
     */
    @ExcelProperty(value = "车载摄像头2地址")
    private String carCamera2Ip;

    /**
     * 车载摄像头3地址
     */
    @ExcelProperty(value = "车载摄像头3地址")
    private String carCamera3Ip;

    /**
     * 车载GPS1串口号
     */
    @ExcelProperty(value = "车载GPS1串口号")
    private String carGps1Port;

    /**
     * 车载GPS1波特率
     */
    @ExcelProperty(value = "车载GPS1波特率")
    private String carGps1Baud;

    /**
     * 车载GPS2串口号,大车专用
     */
    @ExcelProperty(value = "车载GPS2串口号,大车专用")
    private String carGps2Port;

    /**
     * 车载GPS2波特率,大车专用
     */
    @ExcelProperty(value = "车载GPS2波特率,大车专用")
    private String carGps2Baud;

    /**
     * 车载信号串口号
     */
    @ExcelProperty(value = "车载信号串口号")
    private String carSignalPort;

    /**
     * 车载信号波特率
     */
    @ExcelProperty(value = "车载信号波特率")
    private String carSignalBaud;

    /**
     * 软件声音类型 0 男声 1 女声
     */
    @ExcelProperty(value = "软件声音类型 0 男声 1 女声")
    private Long carVoiceType;

    /**
     * 软件声音语速 1 2 3 4 5 
     */
    @ExcelProperty(value = "软件声音语速 1 2 3 4 5 ")
    private Long carVoiceSpeed;


    /**
     * GPSX偏移量
     */
    @ExcelProperty(value = "GPSX偏移量")
    private String gpsXOffset;

    /**
     * GPSY偏移量
     */
    @ExcelProperty(value = "GPSY偏移量")
    private String gpsYOffset;

    /**
     * $column.columnComment
     */
    private Date checkTime;

    /**
     * $column.columnComment
     */
    private Date checkTime1;

    /**
     * $column.columnComment
     */
    private String sStarttime;

    /**
     * $column.columnComment
     */
    private String sEndtime;

    /**
     * $column.columnComment
     */
    private String zjhm;

    /**
     * 网关
     */
    @ExcelProperty(value = "网关")
    private String gateway;

    /**
     * 初次登记日期
     */
    @ExcelProperty(value = "初次登记日期")
    private String ccdjrq;

    /**
     * 车辆型号
     */
    @ExcelProperty(value = "车辆型号")
    private String clxh;

    /**
     * $column.columnComment
     */
    private String zdfc;


    private Long personNum;

    /**
     * 车模标记
     */
    private Long carModelRelation;
    /**
     * 场模标记
     */
    private Long carFieldRelation;
}
