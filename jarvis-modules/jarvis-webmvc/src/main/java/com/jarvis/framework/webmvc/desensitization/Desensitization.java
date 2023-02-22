package com.jarvis.framework.webmvc.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月16日
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerializer.class)
public @interface Desensitization {

    /**
     * 敏感类型
     *
     * @return
     */
    SensitiveTypeEnum type();
}
