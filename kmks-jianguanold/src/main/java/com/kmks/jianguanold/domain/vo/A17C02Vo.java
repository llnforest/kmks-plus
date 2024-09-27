package com.kmks.jianguanold.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试设备备案信息下载
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C02Vo {
    private Head head;

    private List<Body> body;

    @Data
    public static class Head {
        // 标记 1成功
        private String code;

        // 提示或错误描述信息
        private String message;

        // 记录条数
        private Integer rownum;
    }

    @Data
    @ExcelIgnoreUnannotated
    public static class Body {
        // 序号
        @ExcelProperty(value = "序号")
        private String xh;

        // 设备编号
        @ExcelProperty(value = "设备编号")
        private String sbbh;

        // 设备描述
        @ExcelProperty(value = "设备描述")
        private String sbms;

        // 制造厂商
        @ExcelProperty(value = "制造厂商")
        private String zzcs;

        // 设备型号
        @ExcelProperty(value = "设备型号")
        private String sbxh;

        // 考试项目
        @ExcelProperty(value = "考试项目")
        private String ksxm;

        // 考试项目说明
        @ExcelProperty(value = "考试项目说明")
        private String ksxmsm;

        // 评判方式
        @ExcelProperty(value = "评判方式")
        private String ppfs;

        // 考场序号
        @ExcelProperty(value = "考场序号")
        private String kcxh;

        // 适用准驾车型范围
        @ExcelProperty(value = "适用准驾车型范围")
        private String syzjcx;

        // 验收日期
        @ExcelProperty(value = "验收日期")
        private String ysrq;

        // 备案单次考试时间
        @ExcelProperty(value = "备案单次考试时间")
        private String badckssj;

        // 备案每小时考试人次
        @ExcelProperty(value = "备案每小时考试人次")
        private String bamxsksrs;

        // 检验有效期止
        @ExcelProperty(value = "检验有效期止")
        private String jyyxqz;

        // 使用状态
        @ExcelProperty(value = "使用状态")
        private String syzt;

        // 创建时间
        @ExcelProperty(value = "创建时间")
        private String cjsj;

        // 更新时间
        @ExcelProperty(value = "更新时间")
        private String gxsj;
    }
}
