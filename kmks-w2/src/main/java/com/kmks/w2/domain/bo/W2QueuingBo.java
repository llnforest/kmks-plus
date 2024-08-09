package com.kmks.w2.domain.bo;

import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 排队信息业务对象 w2_queuing
 *
 * @author Lynn
 * @date 2023-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2QueuingBo extends BaseEntity {

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空",groups = QueryGroup.class)
    @Column(value = "编号")
    private Long id;

    /**
     * 考试编号
     */
    @NotBlank(message = "考试编号不能为空")
    @Column(value = "考试编号")
    private String ksbh;

    /**
     * 考生姓名
     */
    @Column(value = "考生姓名")
    private String xm;

    /**
     * 证件号码
     */
    @Column(value = "证件号码")
    private String zjhm;

    /**
     * 驾校名称
     */
    private String jxmc;

    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空")
    @Column(value = "考车编号")
    private String kcbh;

    /**
     * 车牌号码
     */
    @Column(value = "考车号牌")
    private String kchp;

    /**
     * 线路
     */
    @Column(value = "线路")
    private Long rLine;

    /**
     * 锁定状态
     */
    @Column(value = "锁定状态")
    private Long iLock;

    /**
     * 监管分车
     */
    @Column(value = "签到状态")
    private Long sign;

    /**
     * 考试状态
     */
    @Column(value = "考试状态")
    private String kszt;

    /**
     * 第几次
     */
    @Column(value = "第几次")
    private Long djc;

    /**
     * 缴费
     */
    @Column(value = "缴费状态")
    private String sqks;

    /**
     * 成绩
     */
    @Column(value = "成绩")
    private Long score;

    /**
     * 夜考
     */
    @Column(value = "夜考")
    private String sfyk;

    /**
     * 分配项目
     */
    @Column(value = "分配项目")
    private String ksxm;

    /**
     * 完成项目
     */
    @Column(value = "完成项目")
    private String wcxm;

    /**
     * 考试车型
     */
    @Column(value = "考试车型")
    private String kscx;

    /**
     * 考官姓名
     */
    private String kgname;

    /**
     * 考官证件
     */
    private String kg;

    /**
     * 安全员
     */
    private String sSafe;

    /**
     * 安全员证件
     */
    private String sSafeZjhm;

    /**
     * $column.columnComment
     */
    private String zkzh;

    /**
     * $column.columnComment
     */
    private String ykxms;

    /**
     * $column.columnComment
     */
    private String zkxms;

    /**
     * 排队序号
     */
    @Column(value = "排队序号")
    private Long bdxh;

    /**
     * $column.columnComment
     */
    private Long kscs;

    /**
     * $column.columnComment
     */
    private Long ykcs;

    /**
     * $column.columnComment
     */
    private String wcxms;

    /**
     * $column.columnComment
     */
    private Long kscj;

    /**
     * $column.columnComment
     */
    private Date kssj;

    /**
     * $column.columnComment
     */
    private String cdxmbh;

    /**
     * $column.columnComment
     */
    private String sfyz;

    /**
     * 日考夜考是否分开 默认可以一起考
     */
    private String kslx;

    /**
     * $column.columnComment
     */
    private String jxdm;

    /**
     * $column.columnComment
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "考试日期")
    private Date ksrq;

    /**
     * $column.columnComment
     */
    @Column(value = "分车状态")
    private String zt;

    /**
     * $column.columnComment
     */
    private Date sqsj;

    /**
     * $column.columnComment
     */
    private String qxlx;

    /**
     * $column.columnComment
     */
    private String kcxh;

    /**
     * $column.columnComment
     */
    private String flag;

    /**
     * $column.columnComment
     */
    private String jszt;

    /**
     * $column.columnComment
     */
    private String lsh;

    /**
     * $column.columnComment
     */
    private String zkxmdm;

    /**
     * 指纹
     */
    private String finger;

    /**
     * $column.columnComment
     */
    private String zsfhg;

    /**
     * $column.columnComment
     */
    private Long zcs;

    /**
     * $column.columnComment
     */
    private String kfxm;

    /**
     * $column.columnComment
     */
    private String kcbs;

    /**
     * $column.columnComment
     */
    private String fieldid;

    /**
     * $column.columnComment
     */
    private String kg2;

    /**
     * 考试成绩
     */
    private Long kscc;

    /**
     * $column.columnComment
     */
    private String signcontent;

    /**
     * $column.columnComment
     */
    private String ksyy;

    /**
     * $column.columnComment
     */
    private String jbr;

    /**
     * $column.columnComment
     */
    private String glbm;

    /**
     * $column.columnComment
     */
    private String yycs;

    /**
     * $column.columnComment
     */
    private String bcyykscs;

    /**
     * $column.columnComment
     */
    private String linecode;

    /**
     * $column.columnComment
     */
    private Date xmkssj;

    /**
     * $column.columnComment
     */
    private String message;

    /**
     * $column.columnComment
     */
    private Long iVoiceTimes;

    /**
     * $column.columnComment
     */
    private Date dVoiceDate;

    /**
     * $column.columnComment
     */
    private Long iVoiceReady;

    /**
     * $column.columnComment
     */
    private Long cwcs;

    /**
     * $column.columnComment
     */
    private String kskm;


}
