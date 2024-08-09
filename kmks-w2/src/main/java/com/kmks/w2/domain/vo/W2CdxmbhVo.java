package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 场地项目编号视图对象 w2_cdxmbh
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2CdxmbhVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long nid;

    /**
     * 设备序号
     */
    @ExcelProperty(value = "设备序号")
    @Column(exists = "data")
    private String sbxh;

    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    @Column(exists = "data")
    private Long paramdm;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    @Column(exists = "data")
    private String paramname;

    /**
     * 公安代码
     */
    @ExcelProperty(value = "公安代码")
    @Column(exists = "data")
    private String gadm;
    /**
     * 内部代码
     */
    @ExcelProperty(value = "内部代码")
    @Column(exists = "data")
    private String mdm;

    /**
     * 考试科目
     */
    @ExcelProperty(value = "考试科目")
    @Column(exists = "data")
    private String kskm;

    /**
     * 信息
     */
    private String msg;

    /**
     * 考试状态
     */
    private String kszt;

    /**
     * 中心IP
     */
    private String zxip;

    /**
     * 端口
     */
    private String portms;







    /**
     * 项目IP地址
     */
    private String xmipaddress;

    /**
     * 项目IP端口
     */
    private String xmipport;

    /**
     * 项目IP用户
     */
    private String xmipuser;

    /**
     * 项目IP密码
     */
    private String xmippwd;

    /**
     * 项目IP通道
     */
    private String xmipchanel;

    /**
     * 使用状态
     */
    private String syzt;

    /**
     * 序号
     */
    private String xh;

    /**
     * zzcs
     */
    private String zzcs;


}
