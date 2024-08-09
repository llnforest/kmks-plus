package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * @author Star
 * @description: TODO
 * @date 2023/5/29 16:05
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidCode {
}
