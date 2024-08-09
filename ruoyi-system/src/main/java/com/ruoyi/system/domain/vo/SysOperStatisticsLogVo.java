package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志记录表 oper_log
 *
 * @author Lion Li
 */

@Data
@ExcelIgnoreUnannotated
public class SysOperStatisticsLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作类别（0=软件用户,1=访问参数,2=车辆参数,3=模型参数,4=评判参数）
     */
    @ExcelProperty(value = "操作类别", converter = ExcelDictConvert.class)
   //@ExcelDictFormat(readConverterExp = "0=软件用户,1=访问参数,2=车辆参数,3=模型参数,4=评判参数")
    @ExcelDictFormat(dictType = "sys_operator_type")
    private Integer operatorType;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ExcelProperty(value = "业务类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_oper_type")
    private Integer businessType;

    /**
     * 操作状态（0正常 1异常）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private Integer status;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private String count;
}

