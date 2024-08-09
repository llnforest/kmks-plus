package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 管理计算机访问对象 sys_computer
 *
 * @author Lynn
 * @date 2024-07-10
 */
@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@TableName("sys_computer")
public class SysComputer {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 管理计算机名称
     */
    private String computerName;
    /**
     * 管理计算机位置
     */
    private String computerLocation;
    /**
     * IP地址
     */
    private String ip;
    /**
     * MAC地址
     */
    private String mac;
    /**
     * 备注
     */
    private String remarks;

    private String schoolId;

}
