package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试过程信息对象 w2_flowlog
 *
 * @author ruoyi
 * @date 2023-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_flowlog")
public class W2Flowlog {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 考生编号
     */
    private String ksbh;
    /**
     * 考试项目
     */
    private String ksxm;
    /**
     * 考试状态
     */
    private String kszt;
    /**
     * 考试时间
     */
    private Date kssj;
    /**
     * 考试扣分
     */
    private Long kskf;
    /**
     * 扣分代码
     */
    private String kfdm;
    /**
     * 设备编号
     */
    private String sbbh;
    /**
     * 考试次数
     */
    private Long kscs;
    /**
     * $column.columnComment
     */
    private String scbj;
    /**
     * $column.columnComment
     */
    private String msg;
    /**
     * 考车编号
     */
    private String kcbh;
    /**
     * 照片
     */
    private String zp;
    /**
     * $column.columnComment
     */
    private String zpbs;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * $column.columnComment
     */
    private String xyh;
    /**
     * 公安代码
     */
    private String gadm;
    /**
     * $column.columnComment
     */
    private String icode;
    /**
     * $column.columnComment
     */
    private String imessage;
    /**
     * $column.columnComment
     */
    private String lsh;
    /**
     * $column.columnComment
     */
    private String xtlb;
    /**
     * $column.columnComment
     */
    private String jkid;
    /**
     * $column.columnComment
     */
    private String upxml;
    /**
     * $column.columnComment
     */
    private String upstatus;
    /**
     * $column.columnComment
     */
    private String upret;
    /**
     * $column.columnComment
     */
    private String upjpgxml;
    /**
     * $column.columnComment
     */
    private String jkjpgid;
    /**
     * $column.columnComment
     */
    private Long zpga;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * 考车号牌
     */
    private String kchp;
    /**
     * 考试成绩
     */
    private Long kscj;
    /**
     * 考生姓名
     */
    private String xm;
    /**
     * 考试员1
     */
    private String ksy1;
    /**
     * 考试员2
     */
    private String ksy2;
    /**
     * $column.columnComment
     */
    private String ksysfzhm1;
    /**
     * $column.columnComment
     */
    private String ksysfzhm2;
    /**
     * 添加日期
     */
    private Date addtime;
    /**
     * $column.columnComment
     */
    private String jkUpstatus;
    /**
     * $column.columnComment
     */
    private String jkJpgupstatus;
    /**
     * $column.columnComment
     */
    private String judgeid;
    /**
     * 项目名称
     */
    private String xmmc;
    /**
     * $column.columnComment
     */
    private Date curtime;
    /**
     * $column.columnComment
     */
    private Long iYekao;
    /**
     * $column.columnComment
     */
    private Date uptime;
    /**
     * $column.columnComment
     */
    private Long bigzt;
    /**
     * 约考日期
     */
    private String ykrq;
    /**
     * $column.columnComment
     */
    private String flag;

}
