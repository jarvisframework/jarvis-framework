package com.jarvis.framework.core.exception;

public class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 549177498215440174L;

    public FrameworkException() {
    }

    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameworkException(Throwable cause) {
        super(cause);
    }

    protected FrameworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
