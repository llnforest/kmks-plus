package com.kmks.w2.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class PassRateDetailVo {

    private static final long serialVersionUID = 1L;

    /**
     * 驾校代码
     */
    @ExcelProperty(value = "驾校代码")
    private String schoolCode;

    /**
     * 驾校名称
     */
    @ExcelProperty(value = "驾校名称")
    private String schoolName;


    /**
     * 人数
     */
    @ExcelProperty(value = "人数")
    private int peopleCount;

    /**
     * 人次
     */
    @ExcelProperty(value = "人次")
    private int peopleTimes;

    /**
     * 合格总人数
     */
    @ExcelProperty(value = "合格总人数")
    private int passTotal;

    /**
     * 合格率
     */
    @ExcelProperty(value = "合格率")
    private String passRate;

    /**
     * 一次合格
     */
    @ExcelProperty(value = "一次合格人数")
    private int passFir;

    /**
     * 二次合格
     */
    @ExcelProperty(value = "二次合格人数")
    private int passSec;


    /**
     * 累加
     *
     * @param peopleCount 人们数
     * @param peopleTimes 人*
     * @param passTotal   通过总
     * @param passFir     通过冷杉
     * @param passSec     通过证券交易委员会
     */
    public void AddNum(int peopleCount,int peopleTimes,int passTotal,int passFir,int passSec){
        this.peopleCount += peopleCount;
        this.peopleTimes += peopleTimes;
        this.passTotal += passTotal;
        this.passFir += passFir;
        this.passSec += passSec;
    }

    public void handlePassRate(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.passRate = decimalFormat.format((double) this.getPassTotal() / this.getPeopleCount() * 100) + "%";
    }
}
