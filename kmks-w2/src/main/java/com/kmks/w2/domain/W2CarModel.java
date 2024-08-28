package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车辆模型对象 w2_carmodel
 *
 * @author ghgd
 * @date 2023-03-14
 */
@Data
@TableName("w2_carmodel")
public class W2CarModel{

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "ID")
    private String id;
    /**
     * 车模名称
     */
    private String modelname;
    /**
     * 车模类型
     */
    private String modeltype;
    /**
     * 车模点位树
     */
    private String pointdata;
    /**
     * 状态
     */
    private String state;
    /**
     * 状态
     */
    private String state1;
    /**
     * $column.columnComment
     */
    private String scode;
    /**
     * 车模导入点位数
     */
    private String temppointdata;
    /**
     * 车牌号
     */
    private String cph;
    /**
     * 车模标记
     */
    private Long relationId;

}
