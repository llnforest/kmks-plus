package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 考试项目开始
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C52Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	数据写入成功
        //0	存在作弊嫌疑，已被取消或暂停考试
        //-1	无考生身份比对信息
        //-2	考试项目与考试安排信息不符
        //-3	考试设备未备案
        //-4	考试设备与考试项目不符
        //-5	考试设备使用状态异常
        //-6	考生考试车型与考试设备使用准驾车型范围不符
        //-7	该考生存在作弊嫌疑，已被暂停/取消考试
        //-8	项目开始时间不能小于科目开始时间
        //-9	存在未结束的考试项目、不能开始新的项目考试！
        //-10	科目三考车号牌不能为空
        //-11	同一考车存在未结束考试，不能开始应用于新的考试
        //-12	未找到考场记录
        //-13	未找到考车信息
        //-14	随机抽取考生的考车与当前考车不一致
        //-15	考试过程信息必须由考车上传
        //-90	考试项目已经开始，无需重传
        //$E	系统异常
        //-20	未按照考试监管的分配进行考试
        private String code;
        // 提示或错误描述信息
        private String message;
        // 加密串
        private String keystr;
    }



}
