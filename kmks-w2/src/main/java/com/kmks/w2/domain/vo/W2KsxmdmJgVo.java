package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 项目代码视图对象 w2_ksxmdm_jg
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2KsxmdmJgVo {

    private static final long serialVersionUID = 1L;

    /**
     * 项目代码
     */
    @ExcelProperty(value = "项目代码")
    private String id;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String name;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    private String kskm;

    /**
     * 项目序号
     */
    @ExcelProperty(value = "项目序号")
    private String custxh;

    /**
     * $column.columnComment
     */
    private Long kmtime1;

    /**
     * $column.columnComment
     */
    private Long kmtime2;

    /**
     * $column.columnComment
     */
    private Long kmtime3;

    @TableField(exist = false)
    private String counts;


}
