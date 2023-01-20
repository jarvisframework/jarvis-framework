package com.jarvis.framework.core;

/**
 * <p>基础转换器</p>
 *
 * @author 王涛
 * @since 1.0, 2021-01-06 17:17:47
 */
public abstract class Converter<F, T> {

    /**
     * 正向转化
     *
     * @param f 正向被转换对象
     * @return 正向转换后对象
     */
    protected abstract T doForward(F f);

    /**
     * 逆向转化
     *
     * @param t 逆向被转换对象
     * @return 逆向转换后对象
     */
    protected abstract F doBackward(T t);

}
