package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.ExcelDictFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基础编码对象 w2_system
 *
 * @author ruoyi
 * @date 2023-03-27
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = false)
@TableName("w2_system")
public class W2System{

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
    @TableId(value = "nid")
    private Long nid;
    /**
     * 考场信息
     */

    private Long type;
    /**
     * 驾校代码
     */
    private String paramdm;
    /**
     * 驾校名称
     */
    private String paramname;
    /**
     * $column.columnComment
     */
    private String bz;
    /**
     * 场次,0:全天 1:上午 2:下午
     */
    private String paramtype;
    /**
     * $column.columnComment
     */
    private String jxqc;
    /**
     * $column.columnComment
     */
    private String maxdm;

}
