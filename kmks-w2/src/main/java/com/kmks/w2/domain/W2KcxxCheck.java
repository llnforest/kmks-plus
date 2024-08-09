package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考车自检对象 w2_kcxx_check
 *
 * @author Lynn
 * @date 2024-06-19
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@TableName("w2_kcxx_check")
public class W2KcxxCheck {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 考车编号
     */
    private String kcbh;
    /**
     * 自检结果
     */
    private Long checkResult;
    /**
     * 自检内容
     */
    private String checkContent;
    /**
     * 自检时间
     */
    private Date checkTime;

}
