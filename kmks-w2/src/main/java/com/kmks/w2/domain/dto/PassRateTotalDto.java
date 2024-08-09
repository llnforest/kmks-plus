package com.kmks.w2.domain.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 总计合格率对象
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
public class PassRateTotalDto {

    private static final long serialVersionUID = 1L;

    public PassRateTotalDto(String name){
        this.name = name;
    }
    /**
     * 统计类型
     */
    private String name;

    /**
     * 合计
     */
    private int total;
    
    public void setTotal(int total){
        this.total += total;
    }

    /**
     * 准驾
     */
    private int a1;

    public void setA1(int a1){
        this.a1 += a1;
    }
    /**
     * 准驾
     */
    private int a2;

    public void setA2(int a2){
        this.a2 += a2;
    }

    /**
     * 准驾
     */
    private int a3;

    public void setA3(int a3){
        this.a3 += a3;
    }

    /**
     * 准驾
     */
    private int b1;

    public void setB1(int b1){
        this.b1 += b1;
    }

    /**
     * 准驾
     */
    private int b2;

    public void setB2(int b2){
        this.b2 += b2;
    }

    /**
     * 准驾
     */
    private int c1;
    public void setC1(int c1){
        this.c1 += c1;
    }

    /**
     * 准驾
     */
    private int c2;
    public void setC2(int c2){
        this.c2 += c2;
    }

    /**
     * 准驾
     */
    private int c5;

    public void setC5(int c5){
        this.c5 += c5;
    }

    /**
     * 准驾
     */
    private int c6;
    public void setC6(int c6){
        this.c6 += c6;
    }

}
