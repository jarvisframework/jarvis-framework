package com.jarvis.framework.web.rest;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {
    public static final String ERROR_CODE = "ERROR";
    public static final String CLIENT_CODE = "CLIENT";
    public static final String OK_CODE = "OK";
    public static final String BIZ_CODE = "BIZ";
    public static final String INFO_CODE = "INFO";
    private static final long serialVersionUID = -4634313262913864170L;
    private int status;
    private T body;
    private String code;

    public RestResponse() {
        this.status = HttpStatus.OK.value();
        this.code = "OK";
    }

    public RestResponse(T body) {
        this();
        this.body = body;
    }

    public RestResponse(int status, T body) {
        this(status, body, (String)null);
        if (status < 200) {
            this.code = "INFO";
        } else if (status < 300) {
            this.code = "OK";
        } else if (status < 400) {
            this.code = "BIZ";
        } else if (status < 500) {
            this.code = "CLIENT";
        } else {
            this.code = "ERROR";
        }

    }

    public RestResponse(int status, T body, String code) {
        this.status = status;
        this.body = body;
        this.code = code;
    }

    public RestResponse(HttpStatus status, T body) {
        this(status.value(), body);
    }

    public RestResponse(HttpStatus status, T body, String code) {
        this(status.value(), body, code);
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return this.status < 300;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static <T> RestResponse<T> success(T body) {
        return new RestResponse(body);
    }

    public static <T> RestResponse<T> info(T body) {
        return new RestResponse(HttpStatus.CONTINUE, body, "INFO");
    }

    public static <T> RestResponse<T> failure(T body) {
        return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR, body, "BIZ");
    }

    public static <T> RestResponse<T> error(T body) {
        return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR, body, "ERROR");
    }

    public static <T> RestResponse<T> response(HttpStatus status, T body) {
        return new RestResponse(status, body);
    }

    public static <T> RestResponse<T> response(int status, T body) {
        return new RestResponse(status, body);
    }
}
