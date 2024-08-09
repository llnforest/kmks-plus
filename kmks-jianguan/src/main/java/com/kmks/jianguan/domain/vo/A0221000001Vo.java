package com.kmks.jianguan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 11:09
 */
@Data
@AllArgsConstructor
public class A0221000001Vo {
    // 标记，1-比对通过；其他代码见业务接口错误码
    private String code;
    // 提示或错误描述信息
    private String message;
    // 人证比对错误标记
    private String retval;
}
