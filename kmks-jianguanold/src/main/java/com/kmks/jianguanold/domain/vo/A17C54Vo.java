package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 考试过程图片
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C54Vo {

    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	数据写入成功
        //-1	无当前考试项目开始信息
        //-2	考生身份证明号码与考生预约信息不符
        //-3	考试项目不正确
        //-4	扣分时间写入错误
        //-90	同一时间已传入抓拍照片
        private String code;
        // 提示或错误描述信息
        private String message;
        // 加密串
        private String keystr;
    }



}
