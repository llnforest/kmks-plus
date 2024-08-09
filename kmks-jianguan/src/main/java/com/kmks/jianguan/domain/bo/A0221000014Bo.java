package com.kmks.jianguan.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kmks.jianguan.validate.CustomGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A0221000014Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空",groups = {CustomGroup.class})
    private String sfzmhm;

    //考试科目
    @NotBlank(message = "考试科目不能为空",groups = {CustomGroup.class})
    private String kskm;

    //结束时间(yyyy-MM-dd hh24:mi:ss)
    @NotNull(message = "结束时间不能为空",groups = {CustomGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jssj;

    //考试成绩
    @NotBlank(message = "考试成绩不能为空",groups = {CustomGroup.class})
    private String kscj;

    //更换考生(默认为 0。0：不更换，1：更换。)
    private String ghks;

}
