package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 考试扣分
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C53Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	数据写入成功
        //0	已存在同一时间的同一扣分记录
        //-1	无当前考试项目开始信息
        //-2	扣分时间大于项目开始时间！
        //-3	考试项目与扣分项不符
        //-4	项目考试过程中，请传入当前考试项目代码
        //-5	考生未进行身份认证
        //-6	扣分时间写入错误
        //-7	项目考试过程信息记录被非法篡改
        //-12	未找到考场记录
        //-13	未找到考车信息
        //-15	考试过程信息必须由考车上传
        private String code;
        // 提示或错误描述信息
        private String message;
        // 加密串
        private String keystr;
    }



}
