package com.kmks.w2.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;


/**
 * 过程明细业务对象 w2_kcxx
 *
 * @author lynn
 * @date 2023-03-14
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class W2KcxxBo{

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    @Column("ID")
    private Long id;

    /**
     * 考车序号
     */
    @NotBlank(message = "考车序号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("考车序号")
    private String kch;

    /**
     * 考车名称
     */
    @NotBlank(message = "考车名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("考车名称")
    private String cph;

    /**
     * 车型名称
     */
    @NotBlank(message = "车型名称不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车型名称")
    private String kcmc;

    /**
     * 考试车型
     */
    @NotBlank(message = "考试车型不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("考试车型")
    private String kscx;

    /**
     * 考车状态
     */
    @NotBlank(message = "考车状态不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("考车状态")
    private String zt;

    /**
     * 项目序号
     */
    @NotBlank(message = "项目序号不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("项目序号")
    private String xmxh;

    /**
     * 考试路线
     */
    private Long line;

    /**
     * 车载视频IP
     */
    @NotBlank(message = "车载视频IP不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车载视频IP")
    private String czip;

    /**
     * 车载用户
     */
    @NotBlank(message = "车载用户不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车载用户")
    private String cuser;

    /**
     * 车载密码
     */
    @NotBlank(message = "车载密码不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车载密码")
    private String cpwd;

    /**
     * 中心视频IP
     */
    @NotBlank(message = "中心视频IP不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("中心视频IP")
    private String zxip;

    /**
     * 中心视频用户
     */
    @NotBlank(message = "中心视频用户不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("中心视频用户")
    private String zuser;

    /**
     * 中心视频密码
     */
    @NotBlank(message = "中心视频密码不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("中心视频密码")
    private String zpwd;

    /**
     * 四合一视频IP
     */
    @NotBlank(message = "四合一视频IP不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("四合一视频IP")
    private String fourip;

    /**
     * 四合一视频用户
     */
    @NotBlank(message = "四合一视频用户不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("四合一视频用户")
    private String fouruser;

    /**
     * 四合一视频密码
     */
    @NotBlank(message = "四合一视频密码不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("四合一视频密码")
    private String fourpwd;

    /**
     * 车辆ip
     */
    @NotBlank(message = "车辆ip不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车辆ip")
    private String carIp;

    /**
     * 车辆mac
     */
    @NotBlank(message = "车辆mac不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("车辆mac")
    private String carMac;

    /**
     * 软件版本
     */
    @NotBlank(message = "软件版本不能为空", groups = { AddGroup.class, EditGroup.class })
    @Column("软件版本")
    private String carVersion;



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
     * GPSX偏移量
     */
    private String gpsXOffset;

    /**
     * GPSY偏移量
     */
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
    private String gateway;

    /**
     * 初次登记日期
     */
    private String ccdjrq;

    /**
     * 车辆型号
     */
    private String clxh;

    /**
     * $column.columnComment
     */
    private String zdfc;

    /**
     * 车模标记
     */
    private Long carModelRelation;
    /**
     * 场模标记
     */
    private Long carFieldRelation;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
