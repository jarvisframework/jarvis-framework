package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.search.ComposedCondition;

import java.io.Serializable;
import java.util.function.Consumer;

public class EntityDelete<T extends Serializable> extends CriteriaDelete<Getter<T>> {

    public static <T extends Serializable> EntityDelete<T> create() {
        return new EntityDelete();
    }

    public EntityDelete<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public EntityDelete<T> filter(BuildFunction<ComposedCondition<Getter<T>>> function) {
        super.filter(function);
        return this;
    }

    public EntityDelete<T> filter(Consumer<ComposedCondition<Getter<T>>> function) {
        super.filter(function);
        return this;
    }
}
