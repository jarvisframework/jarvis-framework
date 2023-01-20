package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.web.rest.RestResponse;

public class ValidateCodeExceptionProcessor implements ExceptionProcessor {
    public ValidateCodeExceptionProcessor() {
    }

    public boolean support(Exception a) {
        return ValidateCodeException.class.isAssignableFrom(a.getClass());
    }

    public RestResponse<?> process(Exception a) {
        return RestResponse.failure(a.getMessage());
    }
}
