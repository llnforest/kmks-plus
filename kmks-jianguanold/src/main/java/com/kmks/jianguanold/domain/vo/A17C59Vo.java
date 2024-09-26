package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 暂停考场考试 (未申请)
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C59Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记 1成功；小于0失败
        private String code;
        // 提示或错误描述信息
        private String message;
    }



}
