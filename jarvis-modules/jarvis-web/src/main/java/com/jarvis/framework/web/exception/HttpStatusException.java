package com.jarvis.framework.web.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月23日
 */
public class HttpStatusException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6676965494395477511L;

    private final HttpStatus httpStatus;

    public HttpStatusException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatusException(HttpStatus httpStatus, Throwable throwable) {
        super(throwable);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    /**
     * 创建对象
     *
     * @param httpStatus Http状态码
     * @param message 错误信息
     * @return HttpStatusException
     */
    public static HttpStatusException create(HttpStatus httpStatus, String message) {
        return new HttpStatusException(httpStatus, message);
    }

}
