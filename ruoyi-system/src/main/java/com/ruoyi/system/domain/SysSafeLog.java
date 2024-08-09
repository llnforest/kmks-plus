package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全日志对象 sys_safe_log
 *
 * @author ghgd
 * @date 2023-03-03
 */
@Data
@TableName("sys_safe_log")
public class SysSafeLog{

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户代码
     */
    private String userNo;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 操作IP
     */
    private String userIp;
    /**
     * 业务类别
     */
    private String category;
    /**
     * 操作类型
     */
    private String operation;
    /**
     * 操作结果
     */
    private String state;
    /**
     * 操作内容
     */
    private String content;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 校验位
     */
    private String code;
    /**
     * 终端机器名
     */
    private String hostName;
    /**
     * 业务类型
     */
    private String ywtpye;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
}
