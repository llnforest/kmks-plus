package com.ruoyi.common.exception.hc;

import com.ruoyi.common.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class HcException extends BaseException {
    private static final long serialVersionUID = 1L;

    public HcException(String code, Object... args) {
        super("HC", code, args, null);
    }
}
