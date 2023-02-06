package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.search.ComposedCondition;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月27日
 */
public class EntityDelete<T extends Serializable> extends CriteriaDelete<Getter<T>> {

    /**
     * 创建对象
     *
     * @param <T> 实体类型
     * @return EntityDelete
     */
    public static <T extends Serializable> EntityDelete<T> create() {
        return new EntityDelete<T>();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#tableName(java.lang.String)
     */
    @Override
    public EntityDelete<T> tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public EntityDelete<T> filter(BuildFunction<ComposedCondition<Getter<T>>> function) {
        super.filter(function);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#filter(java.util.function.Consumer)
     */
    @Override
    public EntityDelete<T> filter(Consumer<ComposedCondition<Getter<T>>> function) {
        super.filter(function);
        return this;
    }

}
