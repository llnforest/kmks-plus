package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 测试数据对象 w2_user_test
 *
 * @author lynn
 * @date 2024-09-24
 */
@Data
@TableName("w2_user_test")
public class W2UserTest {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 考生姓名
     */
    private String xm;
    /**
     * 证件号码
     */
    private String zjhm;

    /**
     * 考试车型
     */
    private String kscx;

    /**
     * 约考日期
     */
    private String ykrq;

}
