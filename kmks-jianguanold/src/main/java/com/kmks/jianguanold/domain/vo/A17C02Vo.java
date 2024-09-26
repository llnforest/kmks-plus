package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试设备备案信息下载
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C02Vo {
    private Head head;

    private List<Body> body;

    @Data
    public static class Head{
        // 标记 1成功
        private String code;

        // 提示或错误描述信息
        private String message;

        // 记录条数
        private Integer rownum;
    }

    @Data
    public static class Body {
        // 序号
        private String xh;

        // 设备编号
        private String sbbh;

        // 设备描述
        private String sbms;

        // 制造厂商
        private String zzcs;

        // 设备型号
        private String sbxh;

        // 考试项目
        private String ksxm;

        // 考试项目说明
        private String ksxmsm;

        // 评判方式
        private String ppfs;

        // 考场序号
        private String kcxh;

        // 适用准驾车型范围
        private String syzjcx;

        // 验收日期
        private String ysrq;

        // 备案单次考试时间
        private String badckssj;

        // 备案每小时考试人次
        private String bamxsksrs;

        // 检验有效期止
        private String jyyxqz;

        // 使用状态
        private String syzt;

        // 创建时间
        private String cjsj;

        // 更新时间
        private String gxsj;
    }
}
