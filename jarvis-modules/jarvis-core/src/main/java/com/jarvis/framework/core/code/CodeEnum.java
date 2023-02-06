package com.jarvis.framework.core.code;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月29日
 */
public interface CodeEnum<T> {

    /**
     * 编码值
     *
     * @return T
     */
    T value();

    /**
     * 编码名称
     *
     * @return String
     */
    String text();

}
