package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 随机安排考试
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17CB3Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	随机分配成功
        //-1	考车信息不正确
        //-2	考场信息不正确
        //-3	考试员信息不正确
        //-4	随机分配出现错误
        private String code;

        // 成功则返回当前分配的考生身份证明号码，失败则返回失败信息描述
        private String message;

        // 对于科目三，成功返回当前分组科目三需考试的线路组
        private String keystr;
    }

}
