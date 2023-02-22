package com.jarvis.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 忽略更新字段
 *
 * @author Doug Wang
 * @version 1.0.0 2022年7月21日
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface IgnoreUpdate {

}
