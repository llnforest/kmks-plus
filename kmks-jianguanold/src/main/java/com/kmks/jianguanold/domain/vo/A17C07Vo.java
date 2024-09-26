package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 考试分组明细信息下载
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C07Vo {
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

        // 身份证明号码
        private String sfzmhm;

        // 姓名
        private String xm;

        // 代理人
        private String dlr;

        // 考车号牌
        private String kchp;

        // 驾校序号
        private String jxxh;
    }
}
