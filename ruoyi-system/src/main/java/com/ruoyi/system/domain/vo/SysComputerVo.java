package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 管理计算机访问视图对象 sys_computer
 *
 * @author Lynn
 * @date 2024-07-10
 */
@Data
@ExcelIgnoreUnannotated
public class SysComputerVo {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Long id;

    /**
     * 管理计算机名称
     */
    @ExcelProperty(value = "管理计算机名称")
    private String computerName;

    /**
     * 管理计算机位置
     */
    @ExcelProperty(value = "管理计算机位置")
    private String computerLocation;

    /**
     * IP地址
     */
    @ExcelProperty(value = "IP地址")
    private String ip;

    /**
     * MAC地址
     */
    @ExcelProperty(value = "MAC地址")
    private String mac;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remarks;


}
