package com.jarvis.framework.function;

@FunctionalInterface
public interface BuildFunction<T> {
    T build(T var1);
}
