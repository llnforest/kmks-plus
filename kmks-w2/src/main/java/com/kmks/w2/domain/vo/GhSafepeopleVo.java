package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;

/**
 * 安全员管理视图对象 gh_safepeople
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class GhSafepeopleVo {

    private static final long serialVersionUID = 1L;

    /**
     * 安全员姓名
     */
    @ExcelProperty(value = "安全员姓名")
    private String sName;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String sZjhm;

    /**
     * 照片存储
     */
    private byte[] sZp;

    /**
     * 照片路径
     */
    private String sZpUrl;

    /**
     * 状态
     */
    private Long iZt;

    /**
     * 考车编号
     */
    @ExcelProperty(value = "考车编号")
    private String kcbh;

    /**
     * 考车牌号
     */
    @ExcelProperty(value = "考车牌号")
    private String kchp;


}
