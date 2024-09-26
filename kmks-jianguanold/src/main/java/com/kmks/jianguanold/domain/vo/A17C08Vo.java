package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试预约信息下载
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C08Vo {
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
    public static class Body {
        // 流水号
        private String lsh;

        // 考试科目
        private String kskm;

        // 准考证明编号
        private String zkzmbh;

        // 身份证明名称
        private String sfzmmc;

        // 身份证明号码
        private String sfzmhm;

        // 姓名
        private String xm;

        // 考试原因
        private String ksyy;

        // 学习时间
        private String xxsj;

        // 预约日期
        private String yyrq;

        // 约考日期
        private String ykrq;

        // 考试车型
        private String kscx;

        // 考试地点
        private String ksdd;

        // 考试场次
        private String kscc;

        // 考试车辆号牌
        private String kchp;

        // 经办人
        private String jbr;

        // 管理部门
        private String glbm;

        // 代理人
        private String dlr;

        // 考试日期
        private String ksrq;

        // 考试次数
        private String kscs;

        // 考试员1
        private String ksy1;

        // 考试员2
        private String ksy2;

        // 状态
        private String zt;

        // 培训审核日期
        private String pxshrq;

        // 是否夜考
        private String sfyk;

        // 桩考约考日期
        private String zkykrq;

        // 桩考是否合格
        private String zksfhg;

        // 车辆种类
        private String clzl;

        // 教练员
        private String jly;

        // 桩考扣分
        private String zkkf;

        // 场考是否已约
        private String ckyy;

        // 业务办理部门
        private String ywblbm;

        // 预约次数
        private String yycs;

        // 本次预约考试次数
        private String bcyykscs;

        // 缴款标记 0：未缴费，1-已缴费
        private String jkbj;
    }
}
