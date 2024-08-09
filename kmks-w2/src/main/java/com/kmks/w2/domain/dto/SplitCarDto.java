package com.kmks.w2.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 分车DTO对象
 *
 * @author Lynn
 * @date 2023-03-28
 */
@Data
public class SplitCarDto {

    private static final long serialVersionUID = 1L;



    /**
     * 考车编号
     */
    private String kcbh;

    /**
     * 车牌号码
     */
    private String kchp;

    /**
     * 线路
     */
    private Long rLine = 0L;


    /**
     * 分车状态
     */
    private String zt;

    /**
     * 报到序号
     */
    private Long bdxh;



}
