package com.kmks.w2.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试过程信息业务对象
 *
 * @author ruoyi
 * @date 2023-05-24
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class W2FlowBo{

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 考生编号
     */
    @NotBlank(message = "考生编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksbh;

    /**
     * 考试项目
     */
    @NotBlank(message = "考试项目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * 考试状态
     */
    @NotBlank(message = "考试状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kszt;

    /**
     * 考试时间
     */
    @NotNull(message = "考试时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date kssj;

    /**
     * 考试扣分
     */
    @NotNull(message = "考试扣分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kskf;

    /**
     * 扣分代码
     */
    @NotBlank(message = "扣分代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kfdm;

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sbbh;

    /**
     * 考试次数
     */
    @NotNull(message = "考试次数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kscs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String scbj;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msg;

    /**
     * 考车编号
     */
    @NotBlank(message = "考车编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;

    /**
     * 照片
     */
    @NotBlank(message = "照片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zp;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zpbs;

    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zjhm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xyh;

    /**
     * 公安代码
     */
    @NotBlank(message = "公安代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gadm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icode;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String imessage;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lsh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xtlb;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jkid;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String upxml;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String upstatus;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String upret;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String upjpgxml;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jkjpgid;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long zpga;

    /**
     * 考试科目
     */
    @NotBlank(message = "考试科目不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;

    /**
     * 考车号牌
     */
    @NotBlank(message = "考车号牌不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kchp;

    /**
     * 考试成绩
     */
    @NotNull(message = "考试成绩不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kscj;

    /**
     * 考生姓名
     */
    @NotBlank(message = "考生姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xm;

    /**
     * 考试员1
     */
    @NotBlank(message = "考试员1不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy1;

    /**
     * 考试员2
     */
    @NotBlank(message = "考试员2不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksy2;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksysfzhm1;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksysfzhm2;

    /**
     * 添加日期
     */
    @NotNull(message = "添加日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date addtime;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jkUpstatus;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jkJpgupstatus;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String judgeid;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xmmc;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date curtime;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long iYekao;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date uptime;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long bigzt;

    /**
     * 约考日期
     */
    @NotBlank(message = "约考日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ykrq;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String flag;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
