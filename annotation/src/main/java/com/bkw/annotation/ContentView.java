package com.bkw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 布局注入注解
 */
@Target(ElementType.TYPE)   //作用在类节点之上
@Retention(RetentionPolicy.RUNTIME) //作用在运行时期
public @interface ContentView {
    int value() default -1;
}
