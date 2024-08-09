package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 考点信息视图对象 w2_factory
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2FactoryVo {

    private static final long serialVersionUID = 1L;

    /**
     * 考场序号
     */
    @ExcelProperty(value = "考场序号")
    private String xh;

    /**
     * 考场名称
     */
    @ExcelProperty(value = "考场名称")
    private String kcmc;

    /**
     * 适用准驾车型范围
     */
    @ExcelProperty(value = "适用准驾车型范围")
    private String kkcx;

    /**
     * 管理部门
     */
    @ExcelProperty(value = "管理部门")
    private String glbm;

    /**
     * 发证机关
     */
    @ExcelProperty(value = "发证机关")
    private String fzjg;

    /**
     * 考场代码
     */
    @ExcelProperty(value = "考场代码")
    private String kcdddh;


}
