package com.jarvis.framework.core.code;

/**
 * <p>description</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-12-17 14:22:51
 */
public interface CodeEnum<T> {
    T value();

    String text();
}
