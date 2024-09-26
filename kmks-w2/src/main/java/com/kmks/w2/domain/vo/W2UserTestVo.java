package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 测试数据视图对象 w2_user_test
 *
 * @author lynn
 * @date 2024-09-24
 */
@Data
@ExcelIgnoreUnannotated
public class W2UserTestVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 考生姓名
     */
    @ExcelProperty(value = "考生姓名")
    private String xm;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String zjhm;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型")
    private String kscx;

    /**
     * 约考日期
     */
    @ExcelProperty(value = "约考日期")
    private String ykrq;


}
