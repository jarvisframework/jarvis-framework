package com.jarvis.framework.security.validation.code;

import org.springframework.web.context.request.RequestAttributes;

public interface ValidateCodeStoreService {

    void store(RequestAttributes var1, String var2, Object var3);

    Object get(RequestAttributes var1, String var2);

    void remove(RequestAttributes var1, String var2);
    
}
