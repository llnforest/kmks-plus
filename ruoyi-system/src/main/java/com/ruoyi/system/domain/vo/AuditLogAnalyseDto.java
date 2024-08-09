package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;


/**
 * 审计日志视图对象 sys_audit_log
 *
 * @author Lynn
 * @date 2024-07-11
 */
@Data
@ExcelIgnoreUnannotated
public class AuditLogAnalyseDto {

    private static final long serialVersionUID = 1L;



    /**
     * 审计分类
     */
    @ExcelProperty(value = "审计分类", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_audit_cate")
    private String tableComment;


    /**
     * 审计模块
     */
    @ExcelProperty(value = "审计模块")
    private String moduleName;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_audit_operate_type")
    private String operationType;


    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private String count;


}
