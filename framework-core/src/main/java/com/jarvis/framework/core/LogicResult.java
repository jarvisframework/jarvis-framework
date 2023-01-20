package com.jarvis.framework.core;

import java.io.Serializable;
import java.util.List;

/**
 * <p>逻辑结果对象</p>
 *
 * @param <T> 逻辑结果数据泛型
 * @author 王涛
 * @since 1.0, 2020-12-15 19:17:09
 */
public class LogicResult<T> implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1741922556175637206L;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 数据对象
     */
    private T data;
    /**
     * 响应编码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 无参构造器
     */
    public LogicResult() {

    }

    /**
     * 参数构造器
     *
     * @param success 逻辑结果状态
     * @param data    逻辑数据
     * @param message 逻辑消息
     */
    public LogicResult(Boolean success,Integer code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 成功结果
     *
     * @param <T> 逻辑数据泛型
     * @return 返回一个表示逻辑处理成功的结果
     */
    public static <T> LogicResult<T> success() {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(true);
        result.setCode(BusCodeEnum.SUCCESS.getCode());
        return result;
    }

    /**
     * 成功结果
     *
     * @param code 成功编码
     * @param data 逻辑数据
     * @param message 返回消息
     * @return 返回一个表示逻辑处理成功的结果
     */
    public static <T> LogicResult<T> success(Integer code,T data,String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(true);
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }
    /**
     * 成功结果
     *
     * @param data 逻辑数据
     * @param <T>  逻辑数据泛型
     * @return 返回一个表示逻辑处理成功的结果
     */
    public static <T> LogicResult<T> success(T data) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(true);
        result.setCode(BusCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 成功结果
     *
     * @param message 逻辑消息
     * @return 返回一个表示逻辑处理成功带消息的结果
     */
    public static <T> LogicResult<T> successWithMessage(String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功结果
     *
     * @param data    逻辑数据
     * @param message 逻辑消息
     * @param <T>     逻辑数据泛型
     * @return 返回一个表示逻辑处理成功带数据和消息的结果
     */
    public static <T> LogicResult<T> success(T data, String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(true);
        result.setCode(BusCodeEnum.SUCCESS.getCode());
        result.setData(data);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功结果
     *
     * @param successList 批处理成功条数
     * @return 返回一个表示逻辑处理成功带批处理的结果
     */
    public static <T> LogicResult<BatchProcessResult<T, T>> successWithBatchProcess(List<T> successList) {
        LogicResult<BatchProcessResult<T, T>> result = new LogicResult<>();
        result.setSuccess(true);
        result.setData(new BatchProcessResult<>(successList, null));
        return result;
    }

    /**
     * 成功结果
     *
     * @param successList 批处理成功条数
     * @param failureList 批处理失败条数
     * @return 返回一个表示逻辑处理成功带批处理的结果
     */
    public static <S, F> LogicResult<BatchProcessResult<S, F>> successWithBatchProcess(List<S> successList, List<F> failureList) {
        LogicResult<BatchProcessResult<S, F>> result = new LogicResult<>();
        result.setSuccess(true);
        result.setData(new BatchProcessResult<>(successList, failureList));
        return result;
    }

    /**
     * 失败结果
     *
     * @return 返回一个表示逻辑处理失败的结果
     */
    public static <T> LogicResult<T> failure() {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(false);
        result.setCode(BusCodeEnum.FAIL.getCode());
        return result;
    }

    /**
     * 失败结果
     *
     * @param message 逻辑消息
     * @return 返回一个表示逻辑处理失败的结果
     */
    public static <T> LogicResult<T> failure(String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(false);
        result.setCode(BusCodeEnum.FAIL.getCode());
        result.setMessage(message);
        return result;
    }
    /**
     * 失败结果
     *
     * @param code 消息编码
     * @param message 逻辑消息
     * @return 返回一个表示逻辑处理失败的结果
     */
    public static <T> LogicResult<T> failure(Integer code,String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败结果
     *
     * @param data    逻辑数据
     * @param message 逻辑消息
     * @param <T>     逻辑数据泛型
     * @return 返回一个表示逻辑处理失败带数据和消息的结果
     */
    public static <T> LogicResult<T> failure(T data, String message) {
        LogicResult<T> result = new LogicResult<>();
        result.setSuccess(false);
        result.setData(data);
        result.setCode(BusCodeEnum.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败结果
     *
     * @param failureList 批处理失败条数
     * @return 返回一个表示逻辑处理成功带批处理的结果
     */
    public static <T> LogicResult<BatchProcessResult<T, T>> failureWithBatchProcess(List<T> failureList) {
        LogicResult<BatchProcessResult<T, T>> result = new LogicResult<>();
        result.setSuccess(false);
        result.setData(new BatchProcessResult<>(null, failureList));
        return result;
    }

    /**
     * 失败结果
     *
     * @param successList 批处理成功条数
     * @param failureList 批处理失败条数
     * @return 返回一个表示逻辑处理成功带批处理的结果
     */
    public static <S, F> LogicResult<BatchProcessResult<S, F>> failureWithBatchProcess(List<S> successList, List<F> failureList) {
        LogicResult<BatchProcessResult<S, F>> result = new LogicResult<>();
        result.setSuccess(true);
        result.setData(new BatchProcessResult<>(successList, failureList));
        return result;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
        return "LogicResult{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
