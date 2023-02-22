package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.search.ComposedCondition;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月27日
 */
public class EntityUpdate<T extends Serializable> extends CriteriaUpdate<Getter<T>> {

    /**
     * 创建对象
     *
     * @param <T> 实体类型
     * @return EntityUpdate
     */
    public static <T extends Serializable> EntityUpdate<T> create() {
        return new EntityUpdate<T>();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#update(java.lang.Object, java.lang.Object)
     */
    @Override
    public EntityUpdate<T> update(Getter<T> column, Object value) {
        super.update(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#plus(java.lang.Object, java.lang.Number)
     */
    @Override
    public EntityUpdate<T> plus(Getter<T> column, Number value) {
        super.plus(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#plus(java.lang.Object, java.lang.Object, java.lang.Number)
     */
    @Override
    public EntityUpdate<T> plus(Getter<T> column, Getter<T> valueColumn, Number value) {
        super.plus(column, valueColumn, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#minus(java.lang.Object, java.lang.Number)
     */
    @Override
    public EntityUpdate<T> minus(Getter<T> column, Number value) {
        super.minus(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#minus(java.lang.Object, java.lang.Object, java.lang.Number)
     */
    @Override
    public EntityUpdate<T> minus(Getter<T> column, Getter<T> valueColumn, Number value) {
        super.minus(column, valueColumn, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#concat(java.lang.Object, java.util.function.Consumer)
     */
    @Override
    public EntityUpdate<T> concat(Getter<T> column, Consumer<ConcatColumn<Getter<T>>> concat) {
        super.concat(column, concat);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#tableName(java.lang.String)
     */
    @Override
    public EntityUpdate<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public EntityUpdate<T> filter(BuildFunction<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#filter(java.util.function.Consumer)
     */
    @Override
    public EntityUpdate<T> filter(Consumer<ComposedCondition<Getter<T>>> condition) {
        super.filter(condition);
        return this;
    }

}
