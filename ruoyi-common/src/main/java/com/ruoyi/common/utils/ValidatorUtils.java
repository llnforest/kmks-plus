package com.ruoyi.common.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator 校验框架工具
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {

    private static final Validator VALID = SpringUtils.getBean(Validator.class);

    private static final String IPv4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-5]{2}|2[0-4][0-9])\\.){3}" +
                    "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-5]{2}|2[0-4][0-9])$";
    private static final Pattern pattern = Pattern.compile(IPv4_PATTERN);

    public static boolean isValidIPv4(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> validate = VALID.validate(object, groups);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException("参数校验异常", validate);
        }
    }


}
