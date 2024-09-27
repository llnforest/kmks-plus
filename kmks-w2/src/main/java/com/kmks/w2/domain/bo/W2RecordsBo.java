package com.kmks.w2.domain.bo;

import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 成绩管理业务对象 w2_records
 *
 * @author lynn
 * @date 2023-04-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2RecordsBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    @Column("编号")
    private Long id;

    /**
     * 考生姓名
     */
    @NotBlank(message = "考生姓名不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考生姓名")
    private String xm;

    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考车编号")
    private String kcbh;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("证件号码")
    private String zjhm;

    /**
     * 准考证号
     */
    @NotBlank(message = "准考证号不能为空", groups = {AddGroup.class, EditGroup.class})
    private String zkzh;

    /**
     * 考试车型
     */
    @NotBlank(message = "考试车型不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试车型")
    private String kscx;

    /**
     * 考试原因
     */
    @NotBlank(message = "考试原因不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试原因")
    private String ksyy;

    /**
     * 预约次数
     */
    @NotNull(message = "预约次数不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("预约次数")
    private Long yycs;

    /**
     * 考试日期1
     */
    @NotNull(message = "考试日期1不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试日期1")
    private Date ksrq1;

    /**
     * 开始时间1
     */
    @NotNull(message = "开始时间1不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("开始时间1")
    private Date kssj1;

    /**
     * 结束时间1
     */
    @NotNull(message = "结束时间1不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("结束时间1")
    private Date jssj1;

    /**
     * 扣分信息1
     */
    @NotBlank(message = "扣分信息1不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("预约次数")
    private String kfxx1;

    /**
     * 分数1
     */
    @NotNull(message = "分数1不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("分数1")
    private Long jgfs1;

    /**
     * 考试员1
     */
    @NotBlank(message = "考试员1不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ksy1;

    /**
     * 考试日期2
     */
    @NotNull(message = "考试日期2不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试日期2")
    private Date ksrq2;

    /**
     * 开始时间2
     */
    @NotNull(message = "开始时间2不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("开始时间2")
    private Date kssj2;

    /**
     * 结束时间2
     */
    @NotNull(message = "结束时间2不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("结束时间2")
    private Date jssj2;

    /**
     * 扣分信息2
     */
    @NotBlank(message = "扣分信息2不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("扣分信息2")
    private String kfxx2;

    /**
     * 分数2
     */
    @NotNull(message = "分数2不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("分数2")
    private Long jgfs2;

    /**
     * 考试员2
     */
    @NotBlank(message = "考试员2不能为空", groups = {AddGroup.class, EditGroup.class})
    private String ksy2;

    /**
     * 考试次数
     */
    @NotNull(message = "考试次数不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试次数")
    private Long kscs;

    /**
     * 效验码
     */
    @NotBlank(message = "效验码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String sjjyw;

    /**
     * 打印
     */
    @NotBlank(message = "打印不能为空", groups = {AddGroup.class, EditGroup.class})
    private String sfprint;

    /**
     * 结果
     */
    @NotBlank(message = "结果不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("考试结果")
    private String ksjg;

    /**
     * 路线
     */
    @NotNull(message = "路线不能为空", groups = {AddGroup.class, EditGroup.class})
    @Column("路线")
    private Long line;

    /**
     * 驾校名称
     */
    private String jxmc;

    /**
     * $column.columnComment
     */
    private String ksbh;

    /**
     * $column.columnComment
     */
    private String kscj1;

    /**
     * $column.columnComment
     */
    private String scf1;

    /**
     * $column.columnComment
     */
    private String kscj2;

    /**
     * $column.columnComment
     */
    private String scf2;

    /**
     * $column.columnComment
     */
    private String kscc;

    /**
     * $column.columnComment
     */
    private String czy;

    /**
     * $column.columnComment
     */
    private String zt;

    /**
     * $column.columnComment
     */
    private String ksxm;

    /**
     * $column.columnComment
     */
    private String sqbs;

    /**
     * $column.columnComment
     */
    private Date sqrq;

    /**
     * $column.columnComment
     */
    private String sqyy;

    /**
     * $column.columnComment
     */
    private String sqr;

    /**
     * $column.columnComment
     */
    private String spr;

    /**
     * $column.columnComment
     */
    private Date sprq;

    /**
     * $column.columnComment
     */
    private String sqr1;

    /**
     * $column.columnComment
     */
    private String sfqk;

    /**
     * $column.columnComment
     */
    private String zjzp;

    /**
     * $column.columnComment
     */
    private String jbzp;

    /**
     * $column.columnComment
     */
    private Long dycs;

    /**
     * $column.columnComment
     */
    private String flag;

    /**
     * $column.columnComment
     */
    private String kcxh;

    /**
     * $column.columnComment
     */
    private Date ksrq;

    /**
     * $column.columnComment
     */
    private String kfss1;

    /**
     * $column.columnComment
     */
    private String kfss2;

    /**
     * $column.columnComment
     */
    private String sszp;

    /**
     * $column.columnComment
     */
    private String sSafe;

    /**
     * $column.columnComment
     */
    private String sSafeZjhm;

    /**
     * 驾校代码
     */
    private String jxdm;

    /**
     * $column.columnComment
     */
    private String lsh;

    /**
     * $column.columnComment
     */
    private String kchp;

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
    private String bcyykscs;

    /**
     * $column.columnComment
     */
    private String jkSszpstatus;

    /**
     * $column.columnComment
     */
    private String sfyk;

    /**
     * $column.columnComment
     */
    private String sZjmc;

    /**
     * $column.columnComment
     */
    private String sXmhz;

    /**
     * $column.columnComment
     */
    private String ykrq;

    /**
     * $column.columnComment
     */
    private Long ykflag;

    /**
     * $column.columnComment
     */
    private Long ykdjc;

    /**
     * $column.columnComment
     */
    private Long line2;

    /**
     * $column.columnComment
     */
    private String sCity;

    /**
     * $column.columnComment
     */
    private Long iDiff;

    /**
     * $column.columnComment
     */
    private String dcrk;

    /**
     * $column.columnComment
     */
    private String ksxmdown;

    /**
     * 考试科目
     */
    private String kskm;

    /**
     * $column.columnComment
     */
    private String hlSignUpload;

    /**
     * $column.columnComment
     */
    private String cjupload;

    /**
     * $column.columnComment
     */
    private String autojudge1;

    /**
     * $column.columnComment
     */
    private String autojudge2;

    /**
     * $column.columnComment
     */
    private String kszt;

    private String sbbh;


}
