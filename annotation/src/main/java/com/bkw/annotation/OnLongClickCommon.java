package com.bkw.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 兼容版本长按事件
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnBaseCommon(setCommonListener = "setOnLongClickListener",
        setCommonObjectListener = View.OnLongClickListener.class,
        callbackMethod = "onLongClick")
public @interface OnLongClickCommon {
    int value() default -1;
}
