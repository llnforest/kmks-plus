package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 扣分代码对象 w2_ksxmkfdm_jg
 *
 * @author lynn
 * @date 2024-04-23
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = false)
@TableName("w2_ksxmkfdm_jg")
public class W2KsxmkfdmJg {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 项目代码
     */
    private Long ksxmdm;
    /**
     * 扣分代码
     */
    private String gakfdm;
    /**
     * 扣分类别
     */
    private String gakfmc;
    /**
     * 扣分说明
     */
    private String kfmc;
    /**
     * 扣分值
     */
    private Long kf;
    /**
     * 考试项目
     */
    private String ksxm;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * $column.columnComment
     */
    private String sBeizhu2;
    /**
     * $column.columnComment
     */
    private String ksxmxh;
    /**
     * 状态
     */
    private Long flag;

}
