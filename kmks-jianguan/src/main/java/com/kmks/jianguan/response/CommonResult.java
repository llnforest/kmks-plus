package com.kmks.jianguan.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    // 返回结果 0：失败 1：成功
    private String fhjg;
    // 错误代码
    private String cwdm;
    // 返回信息描述
    private String fhxxms;

    private List<T> data;
}
