package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 驾考参数对象 w2_config
 *
 * @author ruoyi
 * @date 2023-03-01
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = true)
@TableName("W2_CONFIG")
public class W2Config extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 编码
     */
    @TableId(value = "l_incode")
    private Long lIncode;
    /**
     * 标志参数
     */
    private Long iFlag;
    /**
     * 标志类型
     */
    private String sFlag;
    /**
     * 名称
     */
    private String sName;
    /**
     * 标志值
     */
    private Long iValue;
    /**
     * 标志值
     */
    private String sValue;
    /**
     * 备注
     */
    private String sBeizhu;

}
