package com.jarvis.framework.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Doug Wang
 * @version 1.0.0 2022年12月12日
 */
public class GetParameterAuthentionException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = 2073332624577086475L;

    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg   the detail message
     * @param cause the root cause
     */
    public GetParameterAuthentionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public GetParameterAuthentionException(String msg) {
        super(msg);
    }

}
