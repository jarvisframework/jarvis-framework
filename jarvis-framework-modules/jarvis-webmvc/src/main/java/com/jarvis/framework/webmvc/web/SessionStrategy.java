package com.jarvis.framework.webmvc.web;

import org.springframework.web.context.request.RequestAttributes;

public interface SessionStrategy {

    void setAttribute(RequestAttributes var1, String var2, Object var3);

    Object getAttribute(RequestAttributes var1, String var2);

    void removeAttribute(RequestAttributes var1, String var2);
}
