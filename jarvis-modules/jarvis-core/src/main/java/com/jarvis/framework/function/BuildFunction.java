package com.jarvis.framework.function;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
@FunctionalInterface
public interface BuildFunction<T> {

    T build(T t);
}
