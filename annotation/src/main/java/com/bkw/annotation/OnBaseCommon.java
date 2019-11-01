package com.bkw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 专门处理事件三要素的注解
 */
@Target(ElementType.ANNOTATION_TYPE)  //作用在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface OnBaseCommon {
    // 事件三要素：
    //    1. 订阅的方式 - setOnclickListener
    String setCommonListener();

    //2. 事件源对象 - View.OnclickListener
    Class setCommonObjectListener();

    //3. 具体执行的方法 - 消费
    String callbackMethod();
}
