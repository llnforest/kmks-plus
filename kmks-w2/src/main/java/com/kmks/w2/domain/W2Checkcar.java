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
 * 车辆自检对象 w2_checkcar
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("w2_checkcar")
public class W2Checkcar extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 车辆编号
     */
    private String sCarno;
    /**
     * 考车自检时间
     */
    private String sTime;
    /**
     * 考车自检状态
     */
    private String sValue;
    /**
     * 考车自检说明
     */
    private String sNote;
    /**
     * 自检日期
     */
    private Date dtFs;

}
