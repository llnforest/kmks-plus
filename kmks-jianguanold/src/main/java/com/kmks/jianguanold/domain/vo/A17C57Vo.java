package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 查询成绩单上需打印照片
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C57Vo {
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
        // 所拍摄照片的拍摄时间   yyyy-MM-dd hh24:mi:ss
        private String kssj;
    }

}
