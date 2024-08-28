package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 参数配置表 sys_config
 *
 * @author Lion Li
 */

@Data
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@ExcelIgnoreUnannotated
public class SysConfig extends BaseEntity {

    /**
     * 参数主键
     */
    @ExcelProperty(value = "参数主键")
    @TableId(value = "config_id")
    @Column(value = "参数主键")
    private Long configId;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    @Column(value = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @ExcelProperty(value = "参数键名")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    @Column(value = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @ExcelProperty(value = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    @Column(value = "参数键值")
    private String configValue;

    /**
     * 参数类别
     */
    @ExcelProperty(value = "参数类别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "system_config_type")
    @Column(value = "参数类别")
    private String configType;

    /**
     * 备注
     */
    @Column(value = "备注")
    private String remark;

    /**
     * 排序
     */
    @Column(value = "排序")
    private Long sort;

}
