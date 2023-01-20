package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;

public class EntityQuery<T extends Serializable> extends CriteriaQuery<Getter<T>> {

    public static <T extends Serializable> EntityQuery<T> create() {
        return new EntityQuery();
    }

    public EntityQuery<T> filter(BuildFunction<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    public EntityQuery<T> filter(Consumer<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    public EntityQuery<T> columns(Getter<T>... columns) {
        super.columns(columns);
        return this;
    }

    public EntityQuery<T> columns(Collection<Getter<T>> columns) {
        super.columns(columns);
        return this;
    }

    public EntityQuery<T> count() {
        super.count();
        return this;
    }

    public EntityQuery<T> count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    public EntityQuery<T> countColumn(Getter<T> column) {
        super.countColumn(column);
        return this;
    }

    public EntityQuery<T> countColumn(Getter<T> column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    public EntityQuery<T> max(Getter<T> column) {
        super.max(column);
        return this;
    }

    public EntityQuery<T> max(Getter<T> column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    public EntityQuery<T> min(Getter<T> column) {
        super.min(column);
        return this;
    }

    public EntityQuery<T> min(Getter<T> column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    public EntityQuery<T> sum(Getter<T> column) {
        super.sum(column);
        return this;
    }

    public EntityQuery<T> sum(Getter<T> column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    public EntityQuery<T> avg(Getter<T> column) {
        super.avg(column);
        return this;
    }

    public EntityQuery<T> avg(Getter<T> column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    public EntityQuery<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public EntityQuery<T> asc(Getter<T> column) {
        super.asc(column);
        return this;
    }

    public EntityQuery<T> desc(Getter<T> column) {
        super.desc(column);
        return this;
    }

    public EntityQuery<T> validate(Getter<T> column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }
}
