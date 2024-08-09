package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 场地项目编号对象 w2_cdxmbh
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = true)
@TableName("w2_cdxmbh")
public class W2Cdxmbh extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId("nid")
    private Long nid;
    /**
     * 考试项目
     */
    private Long paramdm;
    /**
     * 项目名称
     */
    private String paramname;
    /**
     * 信息
     */
    private String msg;
    /**
     * 考试状态
     */
    private String kszt;
    /**
     * 中心IP
     */
    private String zxip;
    /**
     * 端口
     */
    private String portms;
    /**
     * 内部代码
     */
    private String mdm;
    /**
     * 设备序号
     */
    private String sbxh;
    /**
     * 公安代码
     */
    private String gadm;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * 项目IP地址
     */
    private String xmipaddress;
    /**
     * 项目IP端口
     */
    private String xmipport;
    /**
     * 项目IP用户
     */
    private String xmipuser;
    /**
     * 项目IP密码
     */
    private String xmippwd;
    /**
     * 项目IP通道
     */
    private String xmipchanel;
    /**
     * 使用状态
     */
    private String syzt;
    /**
     * 序号
     */
    private String xh;
    /**
     * zzcs
     */
    private String zzcs;

}
