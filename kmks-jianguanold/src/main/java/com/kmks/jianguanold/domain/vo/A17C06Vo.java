package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试计划分组信息下载
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C06Vo {
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
        // 分组序号
        private String xh;

        // 考试科目
        private String kskm;

        // 考试日期
        private String ksrq;

        // 考试地点
        private String ksdd;

        // 考场序号
        private String kcxh;

        // 考试车型
        private String kscx;

        // 考试场次
        private String kscc;

        // 考车号牌
        private String kchp;

        // 考试员
        private String ksy;

        // 考试项目
        private String ksxm;

        // 管理部门
        private String glbm;

        // 考试线路
        private String ksxl;
    }
}
