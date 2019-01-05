package com.qishui.commontoolslibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhou on 2019/1/5.
 * 初始化控件
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QBindView {
    //设置注解返回值
    int value() default 0;
}
