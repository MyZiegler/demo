package com.wpw.demo.annotation;

import java.lang.annotation.*;

/**
 * 对比两个字段的差异 产生修改日志
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Contrast {

    /**
     * 比较的描述类型
     */
    String value();

    /**
     * 枚举类型的原始数据  a=b&c=d:default
     */
    String metaData() default "";

}
