package com.jarvis.framework.core.exception;

/**
 * <p>业务异常类</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-12-17 14:43:06
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5057887406611670380L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public static BusinessException create() {
        return new BusinessException();
    }

    public static BusinessException create(String format, Object... objs) {
        return new BusinessException(String.format(format, objs));
    }

    public static BusinessException create(Throwable cause, String format, Object... objs) {
        return new BusinessException(String.format(format, objs), cause);
    }

    public static BusinessException create(Throwable cause) {
        return new BusinessException(cause);
    }
}
