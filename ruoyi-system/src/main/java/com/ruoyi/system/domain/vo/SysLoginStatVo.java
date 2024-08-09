package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author Lion Li
 */

@Data
@ExcelIgnoreUnannotated
public class SysLoginStatVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 登录类型
     */
    @ExcelProperty(value = "登录类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=用户登录,1=用户退出,2=用户注册")
    private String loginType;

    /**
     * 登录状态 0成功 1失败
     */
    @ExcelProperty(value = "登录状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String status;

    /**
     * 提示消息
     */
    @ExcelProperty(value = "提示消息")
    private String msg;

    /**
     * 数量统计
     */
     @ExcelProperty(value = "数量统计")
     private Integer counts;


}
