package com.jarvis.framework.web.controller;

import org.springframework.core.GenericTypeResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 所有Controller基类
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月22日
 */
public abstract class BaseController {

    /** Id类型 */
    public static final String ID = "Id";

    /** Entity类型 */
    public static final String ENTITY = "Entity";

    protected final String selectAttributesParameter = "selectAttributes";

    protected final String keywordAttributesParameter = "keywordAttributes";

    /**
     * 获取参数值
     *
     * @param name 参数名称
     * @return String
     */
    protected String getParameter(String name) {
        final ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes());

        return servletRequestAttributes.getRequest().getParameter(name);
    }

    /**
     * Id类型
     * @param clazz contoller
     * @return Class
     */
    protected Class<?> getIdClass(Class<?> clazz) {
        return (Class<?>) GenericTypeResolver.getTypeVariableMap(clazz).entrySet().stream()
                .filter(e -> ID.equals(e.getKey().getName())).map(e -> e.getValue()).findAny().get();
    }

}
