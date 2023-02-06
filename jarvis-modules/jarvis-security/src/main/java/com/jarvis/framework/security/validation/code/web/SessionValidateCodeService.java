package com.jarvis.framework.security.validation.code.web;

import com.jarvis.framework.security.validation.code.ValidateCodeStoreService;
import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月26日
 */
public class SessionValidateCodeService implements ValidateCodeStoreService {

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#store(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String, java.lang.Object)
     */
    @Override
    public void store(RequestAttributes request, String name, Object value) {
        request.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#get(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public Object get(RequestAttributes request, String name) {
        return request.getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     *
     * @see com.jarvis.framework.security.validation.code.ValidateCodeStoreService#remove(org.springframework.web.context.request.RequestAttributes,
     *      java.lang.String)
     */
    @Override
    public void remove(RequestAttributes request, String name) {
        request.removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

}
