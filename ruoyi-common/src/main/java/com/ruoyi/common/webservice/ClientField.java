package com.ruoyi.common.webservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/6 15:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientField {
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数类型
     */
    private Class<?> paramType;
    /**
     * 参数值
     */
    private Object paramValue;

}
