package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 安全日志视图对象 sys_safe_log
 *
 * @author ghgd
 * @date 2023-03-03
 */
@Data
@ExcelIgnoreUnannotated
public class SysSafeLogStatVo {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类别
     */
    @ExcelProperty(value = "业务类别")
    private String category;

    /**
     * 数量统计
     */
    @ExcelProperty(value = "数量统计")
    private String counts;



}
