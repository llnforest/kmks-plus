package com.kmks.jianguan.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/9 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    // 数据标识
    private String sjbs;
    private T result;
}
