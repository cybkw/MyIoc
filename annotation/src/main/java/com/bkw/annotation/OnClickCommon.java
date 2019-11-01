package com.bkw.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 兼容版本点击事件
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnBaseCommon(setCommonListener = "setOnClickListener",
        setCommonObjectListener = View.OnClickListener.class,
        callbackMethod = "onClick")
public @interface OnClickCommon {
    int value() default -1;
}
