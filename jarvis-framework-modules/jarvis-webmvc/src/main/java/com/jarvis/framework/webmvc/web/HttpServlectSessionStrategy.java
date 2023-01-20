package com.jarvis.framework.webmvc.web;

import org.springframework.web.context.request.RequestAttributes;

public class HttpServlectSessionStrategy implements SessionStrategy {

    public void setAttribute(RequestAttributes request, String name, Object value) {
        request.setAttribute(name, value, 1);
    }

    public Object getAttribute(RequestAttributes request, String name) {
        return request.getAttribute(name, 1);
    }

    public void removeAttribute(RequestAttributes request, String name) {
        request.removeAttribute(name, 1);
    }
}
