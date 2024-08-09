package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.ValidCode;
import com.ruoyi.common.annotation.ValidField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 黑名单管理对象 sys_user_black
 *
 * @author Lynn
 * @date 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@TableName("sys_user_black")
public class SysUserBlack extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 校验位
     */
    @ValidCode
    private String validCode;
    /**
     * 对象名称
     */
    @ValidField
    private String blackName;
    /**
     * 对象类型
     */
    @ValidField
    private String blackType;
    /**
     * 锁定状态
     */
    @ValidField
    private String isLock;
    /**
     * 锁定原因
     */
    private String remark;

}
