package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 黑名单管理视图对象 sys_user_black
 *
 * @author Lynn
 * @date 2023-03-07
 */
@Data
@ExcelIgnoreUnannotated
public class SysUserBlackVo {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号")
    private Long id;

    /**
     * 校验位
     */
    @ExcelProperty(value = "校验位")
    private String validCode;

    /**
     * 对象名称
     */
    @ExcelProperty(value = "对象名称")
    private String blackName;

    /**
     * 对象类型
     */
    @ExcelProperty(value = "对象类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "black_lock_type")
    private String blackType;

    /**
     * 锁定状态
     */
    @ExcelProperty(value = "锁定状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "black_lock_status")
    private String isLock;

    /**
     * 锁定原因
     */
    @ExcelProperty(value = "锁定原因")
    private String remark;

    /**
     * 锁定时间
     */
    @ExcelProperty(value = "锁定时间")
    private Date createTime;

    /**
     * 解锁人
     */
    @ExcelProperty(value = "解锁人")
    private String updateBy;

    /**
     * 解锁时间
     */
    @ExcelProperty(value = "解锁时间")
    private Date updateTime;


}
