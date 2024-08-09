package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 安全日志视图对象 sys_safe_log
 *
 * @author ghgd
 * @date 2023-03-03
 */
@Data
@ExcelIgnoreUnannotated
public class SysSafeLogVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 用户代码
     */
    @ExcelProperty(value = "用户代码")
    private String userNo;

    /**
     * 用户姓名
     */
    @ExcelProperty(value = "用户姓名")
    private String userName;

    /**
     * 操作IP
     */
    @ExcelProperty(value = "操作IP")
    private String userIp;

    /**
     * 业务类别
     */
    @ExcelProperty(value = "业务类别")
    private String category;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private String operation;

    /**
     * 操作结果
     */
    @ExcelProperty(value = "操作结果")
    private String state;

    /**
     * 操作内容
     */
    @ExcelProperty(value = "操作内容")
    private String content;

    /**
     * mac地址
     */
    @ExcelProperty(value = "mac地址")
    private String mac;

    /**
     * 校验位
     */
    @ExcelProperty(value = "校验位")
    private String code;

    /**
     * 终端机器名
     */
    @ExcelProperty(value = "终端机器名")
    private String hostName;

    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型")
    private String ywtpye;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    @ExcelProperty(value = "操作时间")
    private Date createTime;

}
