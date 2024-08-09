package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 轨迹数据视图对象 w2_gpscontent
 *
 * @author ruoyi
 * @date 2023-04-19
 */
@Data
@ExcelIgnoreUnannotated
public class W2GpscontentVo {

    private static final long serialVersionUID = 1L;
    /**
     * 发送日期
     */
    @ExcelProperty(value = "发送日期")
    private Long sendrq;

    /**
     * 车号
     */
    @ExcelProperty(value = "车号")
    private String carno;

    /**
     * 身份证号码
     */
    @ExcelProperty(value = "身份证号码")
    private String sfzhm;

    /**
     * 车载时间戳
     */
    @ExcelProperty(value = "车载时间戳")
    private Long timeid;

    /**
     * gps报文1
     */
    @ExcelProperty(value = "gps报文1")
    private String gpscon1;

    /**
     * gps报文2
     */
    @ExcelProperty(value = "gps报文2")
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
