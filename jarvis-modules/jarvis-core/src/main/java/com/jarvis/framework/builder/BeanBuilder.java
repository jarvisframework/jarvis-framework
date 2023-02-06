package com.jarvis.framework.builder;

import com.jarvis.framework.function.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Bean对象构造器
 *
 * <pre>
 *
 * User user = BeanBuilder.of(User::new).set(User::setName, "zhang san").set(User::setMail, "aaa@qq.com").build();
 * </pre>
 *
 * @author qiucs
 * @version 1.0.0 2021年3月29日
 */
public class BeanBuilder<T> {

    private final Supplier<T> instantiator;

    private final List<Consumer<T>> modifiers = new ArrayList<>();

    private BeanBuilder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> BeanBuilder<T> of(Supplier<T> instantiator) {
        return new BeanBuilder<>(instantiator);
    }

    public <V> BeanBuilder<T> set(Setter<T, V> consumer, V v) {
        final Consumer<T> c = instance -> consumer.accept(instance, v);
        modifiers.add(c);
        return this;
    }

    public T build() {
        final T value = instantiator.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }
}
