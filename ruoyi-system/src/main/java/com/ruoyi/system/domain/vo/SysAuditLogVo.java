package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.util.Date;


/**
 * 审计日志视图对象 sys_audit_log
 *
 * @author Lynn
 * @date 2024-07-11
 */
@Data
@ExcelIgnoreUnannotated
public class SysAuditLogVo {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Long auditId;

    /**
     * 数据库用户名
     */
    @ExcelProperty(value = "数据库用户名")
    private String username;

    /**
     * 审计分类
     */
    @ExcelProperty(value = "审计分类")
    private String tableComment;

    /**
     * 数据表
     */
    @ExcelProperty(value = "数据表")
    private String tableName;



    /**
     * IP 地址
     */
    @ExcelProperty(value = "IP 地址")
    private String ipAddress;



    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_audit_operate_type")
    private String operationType;

    /**
     * 审计模块
     */
    @ExcelProperty(value = "审计模块")
    private String moduleName;

    /**
     * 审计内容
     */
    @ExcelProperty(value = "审计内容")
    private String auditContent;



    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间")
    private Date operationTime;
    /**
     * 校验位
     */
    private String validCode;


}
