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
public class A0221000012Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空",groups = {CustomGroup.class})
    private String sfzmhm;

    //考试科目
    @NotBlank(message = "考试科目不能为空",groups = {CustomGroup.class})
    private String kskm;

    //考试项目
    @NotBlank(message = "考试项目不能为空",groups = {CustomGroup.class})
    private String ksxm;

    //抓拍时间 yyyy-MM-dd hh24:mi:ss
    @NotNull(message = "抓拍时间不能为空",groups = {CustomGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime zpsj;

    //车速(单位：km/h)
    @NotNull(message = "车速不能为空",groups = {CustomGroup.class})
    private Double cs;

}
