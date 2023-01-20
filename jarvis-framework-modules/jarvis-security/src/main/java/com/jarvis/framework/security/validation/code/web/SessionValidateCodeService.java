package com.jarvis.framework.security.validation.code.web;

import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import org.springframework.web.context.request.RequestAttributes;

public class SessionValidateCodeService implements ValidateCodeStoreService {
    public SessionValidateCodeService() {
    }

    public void store(RequestAttributes request, String name, Object value) {
        request.setAttribute(name, value, 1);
    }

    public Object get(RequestAttributes request, String name) {
        return request.getAttribute(name, 1);
    }

    public void remove(RequestAttributes request, String name) {
        request.removeAttribute(name, 1);
    }
}
