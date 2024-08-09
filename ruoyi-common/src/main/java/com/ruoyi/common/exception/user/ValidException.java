package com.ruoyi.common.exception.user;

/**
 * 验证码错误异常类
 *
 * @author ruoyi
 */
public class ValidException extends UserException {
    private static final long serialVersionUID = 1L;

    public ValidException() {
        super("field.valid.error");
    }
}
