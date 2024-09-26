package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 查询考生成绩单签字信息
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C10Vo {
    private Head head;

    @Data
    public static class Head {
        // 标记 1-正常；0-考试合格未签名；-1-考试未完成；-2-无当日考试记录
        private String code;

        // 提示或错误描述信息
        private String message;

    }


}
