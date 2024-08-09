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
public class PassRateVo {

    private static final long serialVersionUID = 1L;

    /**
     * 驾校名称
     */
    @ExcelProperty(value = "驾校名称")
    private String jxmc;

    private String jxdm;

    /**
     * 考试人次
     */
    @ExcelProperty(value = "考试人次")
    private int peopleTimes;

    /**
     * 考试人数
     */
    @ExcelProperty(value = "考试人数")
    private int peopleNum;

    /**
     * 人次
     */
    @ExcelProperty(value = "合格人次")
    private int passTimes;

    /**
     * 人次
     */
    @ExcelProperty(value = "合格人次")
    private int passNum;

    /**
     * 人次合格率
     */
    @ExcelProperty(value = "人次合格率")
    private String passTimesRate;

    /**
     * 人数合格率
     */
    @ExcelProperty(value = "人数合格率")
    private String passNumRate;

    /**
     * 一次合格人数
     */
    @ExcelProperty(value = "一次合格人数")
    private int passFir;

    /**
     * 一次合格率
     */
    @ExcelProperty(value = "一次合格率")
    private String passFirRate;

    /**
     * 二次合格人数
     */
    @ExcelProperty(value = "二次合格人数")
    private int passSec;


    /**
     * 二次合格率
     */
    @ExcelProperty(value = "二次合格率")
    private String passSecRate;


    /**
     * _添加数字
     *
     * @param vo vo
     */
    public void addData(W2RecordsVo vo){
        this.jxdm = vo.getJxdm();
        // ksjg 1、3： 第一次第二次合格  2、4 代表第一次第二次不合格
        this.peopleNum += vo.getTotalNum();
        switch (vo.getKsjg()){
            case "1":
                this.peopleTimes += vo.getTotalNum();
                this.passTimes += vo.getTotalNum();
                this.passNum += vo.getTotalNum();
                this.passFir += vo.getTotalNum();
                break;
            case "2":
                this.peopleTimes += vo.getTotalNum();
                break;
            case "3":
                this.peopleTimes += 2*vo.getTotalNum();
                this.passTimes += vo.getTotalNum();
                this.passNum += vo.getTotalNum();
                this.passSec += vo.getTotalNum();
                break;
            case "4":
                this.peopleTimes += 2*vo.getTotalNum();
                break;
            default:
                break;

        }
    }

    /**
     * @Description 处理合格率
     * @Param []
     * @return void
     * @Author lynn 11:13 2024/4/30
    **/
    public void handlePassRate(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        this.passTimesRate = decimalFormat.format((double) passTimes / peopleTimes * 100) + "%";
        this.passNumRate = decimalFormat.format((double) passNum / peopleNum * 100) + "%";
        this.passFirRate = decimalFormat.format((double) passFir / peopleNum * 100) + "%";
        this.passSecRate = decimalFormat.format((double) passSec / peopleNum * 100) + "%";
    }
}
