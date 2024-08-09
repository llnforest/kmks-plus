package com.ruoyi.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 审计日志业务对象 sys_audit_log
 *
 * @author Lynn
 * @date 2024-07-11
 */

@Data
public class SysAuditLogBo {

    /**
     * 序号
     */
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

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
