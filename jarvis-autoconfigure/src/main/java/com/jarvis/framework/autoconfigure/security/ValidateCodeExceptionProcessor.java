package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年7月8日
 */
public class ValidateCodeExceptionProcessor implements ExceptionProcessor {

    /**
     *
     * @see com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor#support(Exception)
     */
    @Override
    public boolean support(Exception e) {
        return ValidateCodeException.class.isAssignableFrom(e.getClass());
    }

    /**
     *
     * @see com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor#process(Exception)
     */
    @Override
    public RestResponse<?> process(Exception exception) {
        return RestResponse.failure(exception.getMessage());
    }

}
