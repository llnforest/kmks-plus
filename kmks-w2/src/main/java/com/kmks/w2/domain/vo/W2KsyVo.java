package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 考官信息视图对象 w2_ksy
 *
 * @author lynn
 * @date 2023-04-28
 */
@Data
@ExcelIgnoreUnannotated
public class W2KsyVo {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private String xh;

    /**
     * 所属发证机关
     */
    @ExcelProperty(value = "所属发证机关")
    private String sszd;

    /**
     * 管理部门
     */
    @ExcelProperty(value = "管理部门")
    private String glbm;

    /**
     * 证件名称
     */
    @ExcelProperty(value = "证件名称")
    private String sfzmmc;

    /**
     * 身份证明号码
     */
    @ExcelProperty(value = "身份证明号码")
    private String sfzmhm;

    /**
     * 驾驶证档案编号
     */
    @ExcelProperty(value = "驾驶证档案编号")
    private String dabh;

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
     * 出生日期
     */
    @ExcelProperty(value = "出生日期")
    private String csrq;

    /**
     * 考试准驾车型范围
     */
    @ExcelProperty(value = "考试准驾车型范围")
    private String zkcx;

    /**
     * 考试员证发证日期
     */
    @ExcelProperty(value = "考试员证发证日期")
    private String fzrq;

    /**
     * 考试员证有效期止
     */
    @ExcelProperty(value = "考试员证有效期止")
    private String kszyxqz;

    /**
     * 考试员证状态
     */
    private String ksyzt;

    /**
     * 工作单位
     */
    @ExcelProperty(value = "工作单位")
    private String gzdw;

    /**
     * 经办人
     */
    @ExcelProperty(value = "经办人")
    private String jbr;

    /**
     * 考试员证发证单位
     */
    @ExcelProperty(value = "考试员证发证单位")
    private String fzjg;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String cjsj;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private String gxsj;


}
