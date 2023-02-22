package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Consumer;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月27日
 */
public class EntityQuery<T extends Serializable> extends CriteriaQuery<Getter<T>> {

    /**
     * 创建对象
     *
     * @param <T> 实体
     * @return EntityQuery
     */
    public static <T extends Serializable> EntityQuery<T> create() {
        return new EntityQuery<T>();
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public EntityQuery<T> filter(BuildFunction<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#filter(java.util.function.Consumer)
     */
    @Override
    public EntityQuery<T> filter(Consumer<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#columns(java.lang.Object[])
     */
    @Override
    public EntityQuery<T> columns(Getter<T>... columns) {
        super.columns(columns);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#columns(java.util.Collection)
     */
    @Override
    public EntityQuery<T> columns(Collection<Getter<T>> columns) {
        super.columns(columns);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count()
     */
    @Override
    public EntityQuery<T> count() {
        super.count();
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count(java.lang.String)
     */
    @Override
    public EntityQuery<T> count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object)
     */
    @Override
    public EntityQuery<T> countColumn(Getter<T> column) {
        super.countColumn(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> countColumn(Getter<T> column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object)
     */
    @Override
    public EntityQuery<T> max(Getter<T> column) {
        super.max(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> max(Getter<T> column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object)
     */
    @Override
    public EntityQuery<T> min(Getter<T> column) {
        super.min(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> min(Getter<T> column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object)
     */
    @Override
    public EntityQuery<T> sum(Getter<T> column) {
        super.sum(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> sum(Getter<T> column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object)
     */
    @Override
    public EntityQuery<T> avg(Getter<T> column) {
        super.avg(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> avg(Getter<T> column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#tableName(java.lang.String)
     */
    @Override
    public EntityQuery<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#asc(java.lang.Object)
     */
    @Override
    public EntityQuery<T> asc(Getter<T> column) {
        super.asc(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#desc(java.lang.Object)
     */
    @Override
    public EntityQuery<T> desc(Getter<T> column) {
        super.desc(column);
        return this;
    }

    /**
     * @see com.jarvis.framework.search.CriteriaQuery#validate(java.lang.Object, java.lang.String)
     */
    @Override
    public EntityQuery<T> validate(Getter<T> column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#highlight(com.jarvis.framework.search.Highlight)
     */
    @Override
    public EntityQuery<T> highlight(Highlight highlight) {
        super.highlight(highlight);
        return this;
    }
}
