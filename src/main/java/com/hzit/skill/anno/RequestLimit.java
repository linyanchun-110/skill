package com.hzit.skill.anno;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
//自定义一个限流的注解
public @interface RequestLimit {
    //标识限制次数
    int count() default 5;

    //标识时间
    long time() default 60000;
}

