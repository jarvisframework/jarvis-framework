package com.jarvis.framework.function;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年3月29日
 */
@FunctionalInterface
public interface Setter<T, V> {

    void accept(T t, V v);
}
