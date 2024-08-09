package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.kmks.w2.domain.dto.PassRateTotalDto;
import lombok.Data;

import java.text.DecimalFormat;


/**
 * 总计合格率对象
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class PassRateTotalVo {

    private static final long serialVersionUID = 1L;

    public PassRateTotalVo(PassRateTotalDto dto){
        this.name = dto.getName();
        this.total = String.valueOf(dto.getTotal());
        this.a1 = String.valueOf(dto.getA1());
        this.a2 = String.valueOf(dto.getA2());
        this.a3 = String.valueOf(dto.getA3());
        this.b1 = String.valueOf(dto.getB1());
        this.b2 = String.valueOf(dto.getB2());
        this.c1 = String.valueOf(dto.getC1());
        this.c2 = String.valueOf(dto.getC2());
        this.c5 = String.valueOf(dto.getC5());
        this.c6 = String.valueOf(dto.getC6());
    }

    public PassRateTotalVo(String name,PassRateTotalDto dto1,PassRateTotalDto dto2){
        this.name = name;
        DecimalFormat df = new DecimalFormat("0.00");
        this.total = dto2.getTotal() == 0?"0%":df.format(Double.valueOf(dto1.getTotal())/dto2.getTotal()*100)+"%";
        this.a1 = dto2.getA1() == 0?"0%":df.format(Double.valueOf(dto1.getA1())/dto2.getA1()*100)+"%";
        this.a2 = dto2.getA2() == 0?"0%":df.format(Double.valueOf(dto1.getA2())/dto2.getA2()*100)+"%";
        this.a3 = dto2.getA3() == 0?"0%":df.format(Double.valueOf(dto1.getA3())/dto2.getA3()*100)+"%";
        this.b1 = dto2.getB1() == 0?"0%":df.format(Double.valueOf(dto1.getB1())/dto2.getB1()*100)+"%";
        this.b2 = dto2.getB2() == 0?"0%":df.format(Double.valueOf(dto1.getB2())/dto2.getB2()*100)+"%";
        this.c1 = dto2.getC1() == 0?"0%":df.format(Double.valueOf(dto1.getC1())/dto2.getC1()*100)+"%";
        this.c2 = dto2.getC2() == 0?"0%":df.format(Double.valueOf(dto1.getC2())/dto2.getC2()*100)+"%";
        this.c5 = dto2.getC5() == 0?"0%":df.format(Double.valueOf(dto1.getC5())/dto2.getC5()*100)+"%";
        this.c6 = dto2.getC6() == 0?"0%":df.format(Double.valueOf(dto1.getC6())/dto2.getC6()*100)+"%";
    }
    /**
     * 统计类型
     */
    @ExcelProperty(value = "统计类型")
    private String name;


    /**
     * 合计
     */
    @ExcelProperty(value = "合计")
    private String total;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾A1")
    private String a1;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾A2")
    private String a2;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾A3")
    private String a3;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾B1")
    private String b1;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾B2")
    private String b2;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾C1")
    private String c1;
    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾C2")
    private String c2;
    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾C5")
    private String c5;

    /**
     * 准驾
     */
    @ExcelProperty(value = "准驾C6")
    private String c6;


}
