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
public class A0221000015Bo {
    //考试科目
    @NotBlank(message = "考试科目不能为空",groups = {CustomGroup.class})
    private String kskm;

    //考车号牌
    @NotBlank(message = "考车号牌不能为空",groups = {CustomGroup.class})
    private String kchp;

    //数据类型(301-卫星定位1，302-卫星定位2，303-PLC 信号)
    @NotBlank(message = "数据类型不能为空",groups = {CustomGroup.class})
    private String sjlx;

    //开始时间(yyyy-MM-dd hh24:mi:ss)
    @NotNull(message = "开始时间不能为空",groups = {CustomGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kssj;

    //结束时间(yyyy-MM-dd hh24:mi:ss)
    @NotNull(message = "结束时间不能为空",groups = {CustomGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime jssj;

    //数据内容（Json 格式）
    @NotBlank(message = "数据内容不能为空",groups = {CustomGroup.class})
    private String sjnr;
}
