package com.jarvis.framework.core.web;

import com.jarvis.framework.core.BusCodeEnum;

import java.io.Serializable;

/**
 * <p>WEB端数据响应格式</p>
 *
 * @author 王涛
 * @date 2019-11-05 10:40:50
 */
public class WebResponse implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -8112829334393451670L;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态编码
     */
    private Integer code;

    /**
     * 数据对象
     */
    private Object data;

    /**
     * 响应消息
     */
    private String message;


    /**
     * 响应成功
     *
     * @return
     */
    public static WebResponse success() {
        WebResponse response = new WebResponse();
        response.setSuccess(true);
        response.setCode(BusCodeEnum.SUCCESS.getCode());
        response.setMessage(BusCodeEnum.SUCCESS.getMessages());
        return response;
    }

    /**
     * 响应成功
     *
     * @param data
     * @return
     */
    public static WebResponse success(Object data) {
        WebResponse response = new WebResponse();
        response.setSuccess(true);
        response.setCode(BusCodeEnum.SUCCESS.getCode());
        response.setData(data);
        response.setMessage(BusCodeEnum.SUCCESS.getMessages());
        return response;
    }

    /**
     * 响应成功
     *
     * @param code
     * @param data
     * @param message
     * @return
     */
    public static WebResponse success(Integer code,Object data,String message) {
        WebResponse response = new WebResponse();
        response.setSuccess(true);
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }
    /**
     * 响应成功
     *
     * @param data
     * @param message
     * @return
     */
    public static WebResponse success(Object data, String message) {
        WebResponse response = new WebResponse();
        response.setSuccess(true);
        response.setCode(BusCodeEnum.SUCCESS.getCode());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    /**
     * 响应错误
     *
     * @return
     */
    public static WebResponse failure() {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(BusCodeEnum.FAIL.getCode());
        response.setMessage(BusCodeEnum.FAIL.getMessages());
        return response;
    }

    /**
     * 响应错误，传入对应的code及消息
     * @param code
     * @param message
     * @return
     */
    public static WebResponse failure(Integer code,String message){
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 响应错误
     *
     * @return
     */
    public static WebResponse failure(String message) {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(BusCodeEnum.FAIL.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 响应错误
     *
     * @return
     */
    public static WebResponse failure(Object data) {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(BusCodeEnum.FAIL.getCode());
        response.setData(data);
        response.setMessage(BusCodeEnum.FAIL.getMessages());
        return response;
    }

    /**
     * 响应错误
     *
     * @return
     */
    public static WebResponse failure(BusCodeEnum errorCodeEnum) {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(errorCodeEnum.getCode());
        response.setMessage(errorCodeEnum.getMessages());
        return response;
    }

    /**
     * 响应错误
     *
     * @return
     */
    public static WebResponse failure(Object data, String message) {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(BusCodeEnum.FAIL.getCode());
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    /**
     * 响应异常
     *
     * @return
     */
    public static WebResponse error() {
        WebResponse response = new WebResponse();
        response.setSuccess(false);
        response.setCode(BusCodeEnum.SYS_EXP.getCode());
        response.setMessage(BusCodeEnum.SYS_EXP.getMessages());
        return response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebResponse{" +
                "success=" + success +
                ", code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
