package com.jarvis.framework.autoconfigure.security;

import com.jarvis.framework.webmvc.web.exception.handler.ExceptionProcessor;
import com.jarvis.framework.security.validation.code.ValidateCodeException;
import com.jarvis.framework.web.rest.RestResponse;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年7月8日
 */
public class ValidateCodeExceptionProcessor implements ExceptionProcessor {

    /**
     *
     * @see com.gdda.archives.framework.webmvc.web.exception.handler.ExceptionProcessor#support(java.lang.Exception)
     */
    @Override
    public boolean support(Exception e) {
        return ValidateCodeException.class.isAssignableFrom(e.getClass());
    }

    /**
     *
     * @see com.gdda.archives.framework.webmvc.web.exception.handler.ExceptionProcessor#process(java.lang.Exception)
     */
    @Override
    public RestResponse<?> process(Exception exception) {
        return RestResponse.failure(exception.getMessage());
    }

}
