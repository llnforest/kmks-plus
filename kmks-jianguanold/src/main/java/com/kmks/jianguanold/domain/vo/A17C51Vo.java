package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 身份信息比对
 *
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C51Vo {
    private Head head;

    @Data
    public static class Head {
        // 标记，
        // 1	验证通过
        //-1	无预约信息
        //-2	约考日期不是今天
        //-3	已满当天允许考试次数
        //-4	考生身份不合法
        //-5	科目考试系统不合法
        //-6	无考生分组信息
        //-7	分组考官和考生不匹配
        //-8	考场信息合法
        //-9	考试员信息不合法
        //-10	科目考试系统时间不合法
        //-90	已进行了考试身份验证，无需重复验证！
        //
        //$E	系统异常
        //-20	未按照考试监管的分配进行考试
        private String code;
        // 提示或错误描述信息
        private String message;
        // 返回当前考生所需的考试项目信息，项目之间以英文逗号分割
        private String keystr;
    }


}
