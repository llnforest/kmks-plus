package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.base.BaseException;

/**
 * 用户信息异常类关于Ip
 *
 * @author ruoyi
 */
public class UserIpException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserIpException(String code, Object... args) {
        super("user", code, args, null);
    }
}
