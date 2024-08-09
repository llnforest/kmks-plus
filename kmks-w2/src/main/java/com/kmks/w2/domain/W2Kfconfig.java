package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.ibatis.type.JdbcType;

/**
 * 评判参数对象 w2_kfconfig
 *
 * @author Lynn
 * @date 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("w2_kfconfig")
public class W2Kfconfig{

    private static final long serialVersionUID=1L;

    /**
     * 唯一标识
     */
    @TableId(value = "gakey")
    private String gakey;
    /**
     * 项目名称
     */
    private String gakfmc;
    /**
     * 项目序号
     */
    private Long xmxh;
    /**
     * 项目扣分说明
     */
    private String kfmc;
    /**
     * 参数值
     */
    private String value;
    /**
     * 参数说明
     */
    private String beizhu;
    /**
     * 考试科目
     */
    private String kskm;
    /**
     * 扣分代码
     */
    private String gakfdm;
    /**
     * 评判方式
     */
    private String autoflag;
    /**
     * 考试项目
     */
    private String ksxm;
    /**
     * 项目代码
     */
    private Long ksxmdm;
    /**
     * 扣分值
     */
    private Long kf;
    /**
     * $column.columnComment
     */
    private String id;
    /**
     * 分类标记
     */
    private String paramtype;
    /**
     * $column.columnComment
     */
    private String autoflag1;
    /**
     * $column.columnComment
     */
    private String xmtype;

}
