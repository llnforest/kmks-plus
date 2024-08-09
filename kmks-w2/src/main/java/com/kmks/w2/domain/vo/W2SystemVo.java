package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 基础编码视图对象 w2_system
 *
 * @author ruoyi
 * @date 2023-03-27
 */
@Data
@ExcelIgnoreUnannotated
public class W2SystemVo {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    @Column(exists = "data")
    private Long nid;

    /**
     * 考场信息
     */
    @ExcelProperty(value = "考场信息")
    private Long type;

    /**
     * 驾校代码
     */
    @ExcelProperty(value = "驾校代码")
    @Column(exists = "data")
    private String paramdm;

    /**
     * 驾校名称
     */
    @ExcelProperty(value = "驾校名称")
    @Column(exists = "data")
    private String paramname;

    /**
     * $备注
     */
    private String bz;

    /**
     * 场次,0:全天 1:上午 2:下午
     */
    private String paramtype;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "驾校全称")
    @Column(exists = "data")
    private String jxqc;

    /**
     * $column.columnComment
     */
    private String maxdm;


}
