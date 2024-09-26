package com.kmks.jianguanold.domain.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 考试科目考试结束
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
public class A17C56Vo {
    private Head head;

    @Data
    public static class Head{
        // 标记，
        // 1	数据写入成功
        //-1	无当前科目考试信息
        //-2	考生身份证明号码与考生预约信息不符
        //-3	考试结束时间不正确
        //-4	考试过程中拍摄照片数量少于3张
        //-5	考试项目不符合要求
        //-6	存在未结束的考试项目！
        //-7	传输的考试成绩不可为空
        //-91	考试成绩计算不一致
        //-92	日间考试已结束等待进行夜间考试
        private String code;
        // 提示或错误描述信息
        private String message;
        // 加密串
        private String keystr;
    }



}
