package com.ruoyi.common.exception.api;

import lombok.Data;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
@Data
public class FailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    int code;

    public FailException(String message) {
        super(message);
        setCode(500);
    }

    public FailException(int code, String message) {
        super(message);
        setCode(code);
    }
}
