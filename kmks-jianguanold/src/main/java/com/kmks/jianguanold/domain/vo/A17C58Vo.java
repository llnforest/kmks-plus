package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 读取考生照片信息
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C58Vo {
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
        // 考生照片
        private String zpstr;
    }

}
