package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 查询下载信息视图对象 w2_groups_temp
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2GroupsTempVo {

    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号")
    private String lsh;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String zjhm;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String xm;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String xb;

    /**
     * 准考证号
     */
    @ExcelProperty(value = "准考证号")
    private String zkzmbh;

    /**
     * 考试原因
     */
    @ExcelProperty(value = "考试原因")
    private String ksyy;

    /**
     * 考试日期
     */
    @ExcelProperty(value = "考试日期")
    private String ksrq;

    /**
     * 考试车型
     */
    @ExcelProperty(value = "考试车型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "param_car_type")
    private String kscx;

    /**
     * 考试场次
     */
    @ExcelProperty(value = "考试场次")
    private String kscc;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String xmmc;

    /**
     * 考试员1
     */
    @ExcelProperty(value = "考试员1")
    private String ksy1;

    /**
     * 考试员2
     */
    @ExcelProperty(value = "考试员2")
    private String ksy2;

    /**
     * 驾校代码
     */
    @ExcelProperty(value = "驾校代码")
    private String dlr;

    /**
     * 驾校名称
     */
    @ExcelProperty(value = "驾校名称")
    private String jxmc;

    /**
     * 考试项目
     */
    @ExcelProperty(value = "考试项目")
    private String ksxm;

    /**
     * 预约次数
     */
    @ExcelProperty(value = "预约次数")
    private String yycs;

    /**
     * 考试原因代码
     */
    @ExcelProperty(value = "考试原因代码")
    private String ksyybh;

    /**
     * 是否夜考
     */
    @ExcelProperty(value = "是否夜考")
    private String sfyk;

    /**
     * 本次预约考试次数
     */
    @ExcelProperty(value = "本次预约考试次数")
    private String bcyykscs;

    /**
     * 管理部门
     */
    @ExcelProperty(value = "管理部门")
    private String glbm;

    /**
     * 经办人
     */
    @ExcelProperty(value = "经办人")
    private String jbr;


}
