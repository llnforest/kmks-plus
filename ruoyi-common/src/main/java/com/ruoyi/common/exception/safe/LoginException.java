package com.ruoyi.common.exception.safe;

import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.bean.BeanHelper;
import org.springframework.context.annotation.Bean;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class LoginException extends BaseException {
    private static final long serialVersionUID = 1L;

    public LoginException(String msg) {
        super("safe", msg);
    }
}
