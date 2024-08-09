package com.kmks.w2.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 轨迹数据对象 w2_gpscontent
 *
 * @author ruoyi
 * @date 2023-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("w2_gpscontent")
public class W2Gpscontent extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 车载时间戳
     */
    private Long timeid;
    /**
     * gps报文1
     */
    private String gpscon1;
    /**
     * gps报文2
     */
    private String gpscon2;
    /**
     * 车辆信号报文
     */
    private String sigcon;
    /**
     * 考试过程报文
     */
    private String kscon;
    /**
     * 发送日期
     */
    private Long sendrq;
    /**
     * 车号
     */
    private String carno;
    /**
     * 身份证号码
     */
    private String sfzhm;
    /**
     * $column.columnComment
     */
    private String djc;
    /**
     * 考车编号
     */
    private String kcbh;
    /**
     * 流水号
     */
    private String lsh;
    /**
     * 考车号牌
     */
    private String kchp;

}
