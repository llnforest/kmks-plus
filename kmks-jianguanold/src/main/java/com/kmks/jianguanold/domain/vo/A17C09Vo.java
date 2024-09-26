package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 时间同步
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C09Vo {
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
        // 考试监管系统时间 yyyy-MM-dd hh24:mi:ss
        private String sj;
    }

}
