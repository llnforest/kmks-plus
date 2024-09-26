package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 考试项目结束
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C55Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	数据写入成功
        //-1	无当前考试项目开始信息
        //-2	考生身份证明号码与考生预约信息不符
        //-3	考试项目不正确
        //-4	考试设备序号不正确！
        //-5	考试项目结束时间写入错误
        //-6	考生未进行身份认证
        //-7	项目考试过程信息记录被非法篡改
        //-8	每个考试项目中必须至少抓拍一张照片
        //-9	操作类型格式不正确
        //-12	未找到考场记录
        //-13	未找到考车信息
        //-15	考试过程信息必须由考车上传
        //-90	考试项目已经结束、无需重传
        //-91	实际道路考试，在完成科目考试时统一结束
        private String code;
        // 提示或错误描述信息
        private String message;
        // 加密串
        private String keystr;
    }



}
