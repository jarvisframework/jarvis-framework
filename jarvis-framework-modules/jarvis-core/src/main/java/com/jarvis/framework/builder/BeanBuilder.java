package com.jarvis.framework.builder;

import com.jarvis.framework.function.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BeanBuilder<T> {
    private final Supplier<T> instantiator;
    private final List<Consumer<T>> modifiers = new ArrayList();

    private BeanBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> BeanBuilder<T> of(Supplier<T> instantiator) {
        return new BeanBuilder(instantiator);
    }

    public <V> BeanBuilder<T> set(Setter<T, V> consumer, V v) {
        Consumer<T> c = (instance) -> {
            consumer.accept(instance, v);
        };
        this.modifiers.add(c);
        return this;
    }

    public T build() {
        T value = this.instantiator.get();
        this.modifiers.forEach((modifier) -> {
            modifier.accept(value);
        });
        this.modifiers.clear();
        return value;
    }
}
