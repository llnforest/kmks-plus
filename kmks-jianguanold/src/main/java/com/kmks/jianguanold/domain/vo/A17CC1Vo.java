package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 读取收费信息
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17CC1Vo {

    private Head head;

    @Data
    public static class Head{
        // 标记 -1未查询到考生当天的约考信息；0当前科目未完成缴费；1已完成缴费
        private String code;
        // 提示或错误描述信息
        private String message;
    }




}
