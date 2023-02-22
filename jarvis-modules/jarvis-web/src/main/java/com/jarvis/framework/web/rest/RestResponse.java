package com.jarvis.framework.web.rest;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Rest接口统一返回对象
 *
 * @author Doug Wang
 * @version 1.0.0 2021年9月3日
 */
public class RestResponse<T> implements Serializable {

    /** ERROR: 系统出错 */
    public static final String ERROR_CODE = "ERROR";

    /** CLIENT: 客户端出错 */
    public static final String CLIENT_CODE = "CLIENT";

    /** OK: 成功 */
    public static final String OK_CODE = "OK";

    /** BIZ: 业务出错 */
    public static final String BIZ_CODE = "BIZ";

    /** INFO: 提示信息 */
    public static final String INFO_CODE = "INFO";

    /**
     *
     */
    private static final long serialVersionUID = -4634313262913864170L;

    private int status;

    private T body;

    /**
     *
     * ERROR: 系统出错
     * CLIENT: 客户端出错
     * OK: 成功
     * BIZ: 业务出错
     * INFO: 提示信息
     */
    private String code;

    public RestResponse() {
        this.status = HttpStatus.OK.value();
        this.code = OK_CODE;
    }

    public RestResponse(T body) {
        this();
        this.body = body;
    }

    public RestResponse(int status, T body) {
        this(status, body, null);
        if (status < 200) {
            this.code = INFO_CODE;
        } else if (status < 300) {
            this.code = OK_CODE;
        } else if (status < 400) {
            this.code = BIZ_CODE;
        } else if (status < 500) {
            this.code = CLIENT_CODE;
        } else {
            this.code = ERROR_CODE;
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

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the body
     */
    public T getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 成功标识，status < 300 之间表示成功，否则为失败
     *
     * @return
     */
    public boolean isSuccess() {
        return status < 300;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 成功Rest对象，status=HttpStatus.OK，即status=200
     *
     * @param <T>
     * @param body
     * @return
     */
    public static <T> RestResponse<T> success(T body) {
        return new RestResponse<T>(body);
    }

    /**
     * 提示Rest对象，status=HttpStatus.CONTINUE，即status=100
     *
     * @param <T>
     * @param body
     * @return
     */
    public static <T> RestResponse<T> info(T body) {
        return new RestResponse<T>(HttpStatus.CONTINUE, body, INFO_CODE);
    }

    /**
     * 失败Rest对象，status=HttpStatus.INTERNAL_SERVER_ERROR，即status=500
     *
     * @param <T>
     * @param body
     * @return
     */
    public static <T> RestResponse<T> failure(T body) {
        return new RestResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, body, BIZ_CODE);
    }

    /**
     * 错误Rest对象，status=HttpStatus.INTERNAL_SERVER_ERROR，即status=500
     *
     * @param <T>
     * @param body
     * @return
     */
    public static <T> RestResponse<T> error(T body) {
        return new RestResponse<T>(HttpStatus.INTERNAL_SERVER_ERROR, body, ERROR_CODE);
    }

    /**
     * 自定义Rest对象
     *
     * @param <T>
     * @param status
     * @param body
     * @return
     */
    public static <T> RestResponse<T> response(HttpStatus status, T body) {
        return new RestResponse<T>(status, body);
    }

    /**
     * 自定义Rest对象
     *
     * @param <T>
     * @param status
     * @param body
     * @return
     */
    public static <T> RestResponse<T> response(int status, T body) {
        return new RestResponse<T>(status, body);
    }

}
