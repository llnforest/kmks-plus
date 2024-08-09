package com.ruoyi.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String value() default "";
    String dict() default "";
    String separator() default ",";
    String[] exists() default {};
}
