package com.kmks.jianguan.domain.bo;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kmks.jianguan.validate.CustomGroup;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A0221000009Bo {
    //身份证明号码
    @NotBlank(message = "身份证明号码不能为空",groups = {CustomGroup.class})
    private String sfzmhm;
    
    //考车号牌
    @NotBlank(message = "考车号牌不能为空",groups = {CustomGroup.class})
    private String kchp;
    
    //考试科目
    @NotBlank(message = "考试科目不能为空",groups = {CustomGroup.class})
    private String kskm;

    //考试路线(科目三不可空)
    private String ksxl;

    //开始时间
    @NotNull(message = "开始时间不能为空",groups = {CustomGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kssj;


    @AssertTrue(message = "考试路线不能为空",groups = {CustomGroup.class})
    public boolean isValidateKsxl(){
        return !(kskm.equals("3") && ObjectUtil.isEmpty(ksxl));
    }
}
