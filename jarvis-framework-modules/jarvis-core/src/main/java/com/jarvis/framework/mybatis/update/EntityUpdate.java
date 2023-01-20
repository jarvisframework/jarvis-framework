package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.search.ComposedCondition;

import java.io.Serializable;
import java.util.function.Consumer;

public class EntityUpdate<T extends Serializable> extends CriteriaUpdate<Getter<T>> {

    public static <T extends Serializable> EntityUpdate<T> create() {
        return new EntityUpdate();
    }

    public EntityUpdate<T> update(Getter<T> column, Object value) {
        super.update(column, value);
        return this;
    }

    public EntityUpdate<T> plus(Getter<T> column, Number value) {
        super.plus(column, value);
        return this;
    }

    public EntityUpdate<T> plus(Getter<T> column, Getter<T> valueColumn, Number value) {
        super.plus(column, valueColumn, value);
        return this;
    }

    public EntityUpdate<T> minus(Getter<T> column, Number value) {
        super.minus(column, value);
        return this;
    }

    public EntityUpdate<T> minus(Getter<T> column, Getter<T> valueColumn, Number value) {
        super.minus(column, valueColumn, value);
        return this;
    }

    public EntityUpdate<T> concat(Getter<T> column, Consumer<ConcatColumn<Getter<T>>> concat) {
        super.concat(column, concat);
        return this;
    }

    public EntityUpdate<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public EntityUpdate<T> filter(BuildFunction<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    public EntityUpdate<T> filter(Consumer<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }
}
