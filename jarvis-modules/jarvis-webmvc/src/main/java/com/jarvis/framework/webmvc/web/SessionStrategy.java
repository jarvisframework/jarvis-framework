package com.jarvis.framework.webmvc.web;

import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月15日
 */
public interface SessionStrategy {

    void setAttribute(RequestAttributes request, String name, Object value);

    Object getAttribute(RequestAttributes request, String name);

    void removeAttribute(RequestAttributes request, String name);

}
