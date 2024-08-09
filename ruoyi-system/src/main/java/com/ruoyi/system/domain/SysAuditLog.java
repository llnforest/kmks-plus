package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 审计日志对象 sys_audit_log
 *
 * @author Lynn
 * @date 2024-07-11
 */
@Data
@TableName("sys_audit_log")
public class SysAuditLog {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "audit_id")
    private Long auditId;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据表
     */
    private String tableName;
    /**
     * 审计分类
     */
    private String tableComment;
    /**
     * IP 地址
     */
    private String ipAddress;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 审计内容
     */
    private String auditContent;
    /**
     * 审计模块
     */
    private String moduleName;
    /**
     * 校验位
     */
    private String validCode;

}
