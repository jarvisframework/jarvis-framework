package com.jarvis.framework.webmvc.web;

import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class HttpServlectSessionStrategy implements SessionStrategy {

    /**
     *
     * @see com.jarvis.framework.webmvc.web.SessionStrategy#setAttribute(
     *      rg.springframework.web.context.request.RequestAttributes,
     *      java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public void setAttribute(RequestAttributes request, String name, Object value) {
        request.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    /**
     *
     * @see com.jarvis.framework.webmvc.web.SessionStrategy#getAttribute(
     *      org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public Object getAttribute(RequestAttributes request, String name) {
        return request.getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     *
     * @see com.jarvis.framework.webmvc.web.SessionStrategy#removeAttribute(
     *      org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public void removeAttribute(RequestAttributes request, String name) {
        request.removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

}
