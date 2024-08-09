package com.ruoyi.common.exception.api;

import lombok.Data;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
@Data
public class SuccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Object data;

    public SuccessException() {
        super("success");
    }

    public SuccessException(String message) {
        super(message);
    }

    public SuccessException(String message, Object data) {
        super(message);
        this.setData(data);
    }
}
