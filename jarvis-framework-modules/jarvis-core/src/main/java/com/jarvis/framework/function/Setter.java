package com.jarvis.framework.function;

@FunctionalInterface
public interface Setter<T, V> {
    void accept(T var1, V var2);
}
