package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试员备案信息下载
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C04Vo {
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

        // 所属发证机关
        private String sszd;

        // 管理部门
        private String glbm;

        // 身份证明名称
        private String sfzmmc;

        // 身份证明号码
        private String sfzmhm;

        // 驾驶证档案编号
        private String dabh;

        // 姓名
        private String xm;

        // 性别
        private String xb;

        // 出生日期
        private String csrq;

        // 考试准驾车型范围
        private String zkcx;

        // 考试员证发证日期
        private String fzrq;

        // 考试员证有效期止
        private String kszyxqz;

        //考试员证状态（自己改的）  A正常；B过期；C注销
        private String kszzt;

        // 工作单位
        private String gzdw;

        // 经办人
        private String jbr;

        // 考试员证发证单位
        private String fzjg;

        // 创建时间
        private String cjsj;

        // 更新时间
        private String gxsj;
    }
}
