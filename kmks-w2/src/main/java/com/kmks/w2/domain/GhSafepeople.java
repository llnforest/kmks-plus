package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全员管理对象 gh_safepeople
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gh_safepeople")
public class GhSafepeople extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 安全员姓名
     */
    private String sName;
    /**
     * 证件号码
     */
    @TableId(value = "s_zjhm")
    private String sZjhm;
    /**
     * 照片存储
     */
    private byte[] sZp;
    /**
     * 照片路径
     */
    private String sZpUrl;
    /**
     * 状态
     */
    private Long iZt;
    /**
     * 考车编号
     */
    private String kcbh;
    /**
     * 考车牌号
     */
    private String kchp;

}
