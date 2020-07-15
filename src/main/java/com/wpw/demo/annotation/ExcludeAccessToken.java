package com.wpw.demo.annotation;

import java.lang.annotation.*;

/**
 * 排除JWT token验证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeAccessToken {

}
