package com.jarvis.framework.core.exception;

/**
 * <p>异常基类</p>
 *
 * @author 王涛
 * @date 2019-10-31 17:07:37
 */
public interface IException {

    /**
     * 获取异常编码
     *
     * @return 异常编码
     */
    String getErrorCode();

    /**
     * 获取本地异常信息
     *
     * @return 本地异常信息
     */
    String getNativeMessage();

    /**
     * 设置异常参数
     *
     * @param objects 异常参数
     */
    void setErrorArguments(Object... objects);

    /**
     * 取异常参数
     *
     * @return 异常参数
     */
    Object[] getErrorArguments();

}
