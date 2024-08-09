package com.kmks.jianguan.domain.bo;

import com.kmks.jianguan.validate.CustomGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:30
 */
@Data
public class A0221000006Bo {
    //科目考试系统序号
    @NotBlank(message = "科目考试系统序号不能为空",groups = {CustomGroup.class})
    private String ksxtxh;
}
