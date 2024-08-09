package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 考试记录
 * @author ruoyi
 *
 */
@Data
@ExcelIgnoreUnannotated
public class W2LogNewVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private String ID;
    /**
     * 考试编号
     */
    @ExcelProperty(value = "考试编号")
    private String KSBH;
    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    private String KSXM;
    /**
     * 考试
     */
    @ExcelProperty(value = "考试")
    private String KSZT;
    /**
     * 考试时间
     */
    @ExcelProperty(value = "考试时间")
    private String KSSJ;
    /**
     * 考试扣分
     */
    @ExcelProperty(value = "考试扣分")
    private String KSKF;
    /**
     * 考试代码
     */
    @ExcelProperty(value = "考试代码")
    private String KFDM;
    /**
     * 设备编号
     */
    @ExcelProperty(value = "设备编号")
    private String SBBH;
    /**
     * 考试次数
     */
    @ExcelProperty(value = "考试次数")
    private String KSCS;
    /**
     *上传标记
     */
    @ExcelProperty(value = "上传标记")
    private String SCBJ;
    /**
     * 信息
     */
    @ExcelProperty(value = "信息")
    private String MSG;
    /**
     * 照片
     */
    @ExcelProperty(value = "照片")
    private String ZP;

    /**
     * 照片标识
     */
    @ExcelProperty(value = "照片标识")
    private String ZPBS;
    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String ZJHM;
    /**
     * 信号源
     */
    private String XYH;
    /**
     * 公安代码
     */
    private String GADM;
    private String ICODE;
    private String IMESSAGE;
    /**
     *
     */
    private String LSH;
    private String XTLB;

    private String JKID;
    private String UPXML;
    private String UPSTATUS;
    private String UPRET;
    private String UPJPGXML;
    private String JKJPGID;
    private String ZPGA;
    private String KSKM;
    private String KCHP;
    private String KSCJ;
    private String XM;
    private String KSY1;
    private String KSY2;
    private String KSYSFZHM1;
    private String KSYSFZHM2;
    private String ADDTIME;
    private String JK_UPSTATUS;
    private String JK_JPGUPSTATUS;
    private String JUDGEID;
    private String XMMC;
    private String CURTIME;
    private String I_YEKAO;
    private String UPTIME;
    private String BIGZT;
    private String YKRQ;
    private String FLAG;

}
