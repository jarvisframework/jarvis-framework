package com.jarvis.framework.core.exception;

import java.io.Serializable;

/**
 * <p>业务异常基类</p>
 *
 * @author 王涛
 * @date 2019-10-31 17:07:37
 */
public class BusinessException extends RuntimeException implements IException, Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -7765506836733674464L;

    /**
     * 异常code
     */
    protected String errCode;

    /**
     * 异常信息
     */
    private String nativeMsg;

    /**
     * 异常 arguments
     */
    private Object[] arguments;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(String code, String msg) {
        super(msg);
        this.errCode = code;
    }

    public BusinessException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.errCode = code;
    }

    public BusinessException(String code, String msg, String nativeMsg) {
        super(msg);
        this.errCode = code;
        this.nativeMsg = nativeMsg;
    }

    public BusinessException(String code, String msg, String nativeMsg, Throwable cause) {
        super(msg, cause);
        this.errCode = code;
        this.nativeMsg = nativeMsg;
    }

    public BusinessException(String code, Object... args) {
        super();
        this.errCode = code;
        this.arguments = args;
    }

    public BusinessException(String code, String msg, Object... args) {
        super(msg);
        this.errCode = code;
        this.arguments = args;
    }

    @Override
    public void setErrorArguments(Object... args) {
        this.arguments = args;
    }

    @Override
    public Object[] getErrorArguments() {
        return this.arguments;
    }

    @Override
    public String getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getNativeMessage() {
        return nativeMsg;
    }

}
