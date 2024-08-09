package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * 地图模型视图对象 w2_fieldmap
 *
 * @author ghgd
 * @date 2023-03-15
 */
@Data
@ExcelIgnoreUnannotated
public class W2FieldmapVo {

    /**
     * 主键ID
     */

    private String id;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String fieldname;

    /**
     * 项目编号
     */
    @ExcelProperty(value = "项目编号")
    private String fieldid;

    /**
     * 项目类型
     */
    @ExcelProperty(value = "项目类型")
    private String fieldtype;

    /**
     * 项目点数量
     */
    @ExcelProperty(value = "项目点数量")
    private String pointcount;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String state;

    /**
     * 线路
     */
    @ExcelProperty(value = "线路")
    private String lineno;

    /**
     * 位置数量
     */
    @ExcelProperty(value = "位置数量")
    private String pointposition;

    /**
     * 加密CODE
     */
    @ExcelProperty(value = "加密CODE")
    private String scode;


    /**
     * 考场名称
     */
    @ExcelProperty(value = "考场名称")
    private String schoolname;

    /**
     * $column.columnComment
     */
    @ExcelProperty(value = "项目名称")
    private String projectname;

    /**
     * 导入点数据集合
     */
    @ExcelProperty(value = "导入点数据集合")
    private String temppointdata;

    /**
     * 场模标记
     */
    private Long relationId;
    /**
     * 点数据集合
     */
    @ExcelProperty(value = "点数据集合")
    private String pointdata;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.VARCHAR)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT,jdbcType = JdbcType.DATE)
    private Date createTime;


}
