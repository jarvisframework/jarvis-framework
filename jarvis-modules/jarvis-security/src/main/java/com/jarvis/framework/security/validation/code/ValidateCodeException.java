package com.jarvis.framework.security.validation.code;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月15日
 */
public class ValidateCodeException extends AuthenticationException {

    /**
     * @param msg
     */
    public ValidateCodeException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param cause
     */
    public ValidateCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     *
     */
    private static final long serialVersionUID = 7398882108768410080L;

}
