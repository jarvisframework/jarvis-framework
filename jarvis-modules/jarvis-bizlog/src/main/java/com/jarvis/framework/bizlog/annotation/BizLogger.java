package com.jarvis.framework.bizlog.annotation;

import com.jarvis.framework.bizlog.constant.BizLevel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月14日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BizLogger {

    /** 模块名称 */
    String module() default "";

    /** 操作 */
    String action() default "";

    /** 操作内容 */
    String content() default "";

    /** 日志记录级别，默认WRITE */
    BizLevel level() default BizLevel.WRITE;

}
