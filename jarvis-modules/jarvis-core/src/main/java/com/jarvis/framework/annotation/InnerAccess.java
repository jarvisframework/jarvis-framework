package com.jarvis.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 内部访问控制
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月2日
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface InnerAccess {

}
