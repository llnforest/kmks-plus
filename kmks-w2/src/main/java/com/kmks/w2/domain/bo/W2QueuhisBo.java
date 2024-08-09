package com.kmks.w2.domain.bo;

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
 * 【请填写功能名称】业务对象 w2_queuhis
 *
 * @author ruoyi
 * @date 2024-04-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class W2QueuhisBo extends BaseEntity {

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksbh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zjhm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkzh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kscx;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ykxms;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkxms;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long bdxh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kszt;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kscs;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ykcs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String wcxms;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kscj;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date kssj;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cdxmbh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfyz;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kslx;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long djc;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jxdm;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date ksrq;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksxm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zt;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date sqsj;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jxmc;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String qxlx;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcxh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String flag;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jszt;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kg;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String lsh;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zkxmdm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kgname;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String finger;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sqks;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kchp;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sfyk;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String zsfhg;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long zcs;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long score;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kfxm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String wcxm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kcbs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fieldid;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kg2;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long kscc;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sign;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signcontent;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rLine;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ksyy;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String jbr;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String glbm;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String yycs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String bcyykscs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sSafe;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String sSafeZjhm;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long iLock;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String linecode;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date xmkssj;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String message;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long iVoiceTimes;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date dVoiceDate;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long iVoiceReady;

    /**
     * $column.columnComment
     */
    @NotNull(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cwcs;

    /**
     * $column.columnComment
     */
    @NotBlank(message = "$column.columnComment不能为空", groups = { AddGroup.class, EditGroup.class })
    private String kskm;


}
