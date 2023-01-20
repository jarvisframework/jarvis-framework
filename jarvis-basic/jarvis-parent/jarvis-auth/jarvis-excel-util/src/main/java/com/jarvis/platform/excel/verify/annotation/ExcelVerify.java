package com.jarvis.platform.excel.verify.annotation;

import com.jarvis.framework.core.code.CodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  //  注解用于字段上
@Retention(RetentionPolicy.RUNTIME)  // 保留到运行时，可通过注解获取
public @interface ExcelVerify {

    String msg();

    int length() default 0;

    boolean notEmpty() default false;

    String matches() default "";

    String illegalityChars() default "";

    Class<? extends CodeEnum> codeEnum() default CodeEnum.class;
}
