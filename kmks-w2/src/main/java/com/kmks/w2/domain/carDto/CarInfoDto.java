package com.kmks.w2.domain.carDto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 车辆模型
 * @author Star
 * @description: TODO
 * @date 2024/5/29 10:27
 */
@Data
@AllArgsConstructor
public class CarInfoDto {
    /**
     * ID
     */
    private Long id;

    /**
     * 考车序号
     */
    private String kch;

    /**
     * 考车名称
     */
    private String cph;

    /**
     * 车型名称
     */
    private String kcmc;

    /**
     * 考试车型
     */
    private String kscx;

    /**
     * 考车状态
     */
    private String zt;

    /**
     * 项目序号
     */
    private String xmxh;

    /**
     * 考试路线
     */
    private Long line;

    /**
     * 车载视频IP
     */
    private String czip;

    /**
     * 车载用户
     */
    private String cuser;

    /**
     * 车载密码
     */
    private String cpwd;

    /**
     * 中心视频IP
     */
    private String zxip;

    /**
     * 中心视频用户
     */
    private String zuser;

    /**
     * 中心视频密码
     */
    private String zpwd;

    /**
     * 四合一视频IP
     */
    private String fourip;

    /**
     * 四合一视频用户
     */
    private String fouruser;

    /**
     * 四合一视频密码
     */
    private String fourpwd;

    /**
     * 车辆mac
     */
    private String carMac;

    /**
     * 车载评判软件版本
     */
    private String carVersion;

    /**
     * 车辆ip
     */
    private String carIp;

    /**
     * 车模名称
     */
    private String carModel1Name;

    /**
     * 考试编号
     */
    private String ksbh;

    private String mdzt;

    private Long mdrs;

    private String zc;

    private String lxbs;

    private String msg;

    private String fieldid;

    private String fieldname;

    private Long fieldstatus;

    private String signcheck;

    private String allline;

    private String curline;

    private String xm;

    private String zkxms;

    private String jxmc;

    private Long djc;

    private Long rLine;

    private String kgname;

    private String sSafe;

    private String message;

    private String ipadddress;

    private String macaddress;

    private Long checkResult1;

    private Long checkResult2;

    private Long checkResult3;

    /**
     * 车载摄像头1地址
     */
    private String carCamera1Ip;

    /**
     * 车载摄像头2地址
     */
    private String carCamera2Ip;

    /**
     * 车载摄像头3地址
     */
    private String carCamera3Ip;

    /**
     * 车载GPS1串口号
     */
    private String carGps1Port;

    /**
     * 车载GPS1波特率
     */
    private String carGps1Baud;

    /**
     * 车载GPS2串口号,大车专用
     */
    private String carGps2Port;

    /**
     * 车载GPS2波特率,大车专用
     */
    private String carGps2Baud;

    /**
     * 车载信号串口号
     */
    private String carSignalPort;

    /**
     * 车载信号波特率
     */
    private String carSignalBaud;

    /**
     * 软件声音类型 0 男声 1 女声
     */
    private Long carVoiceType;

    /**
     * 软件声音语速 1 2 3 4 5
     */
    private Long carVoiceSpeed;

    /**
     * 车模名称2
     */
    private String carModel2Name;

    /**
     * GPSX偏移量
     */
    private String gpsXOffset;

    /**
     * GPSY偏移量
     */
    private String gpsYOffset;

    private Date checkTime;

    private Date checkTime1;

    private String sStarttime;

    private String sEndtime;

    private String zjhm;

    /**
     * 网关
     */
    private String gateway;

    /**
     * 初次登记日期
     */
    private String ccdjrq;

    /**
     * 车辆型号
     */
    private String clxh;

    private String zdfc;


    private Long personNum;
}
