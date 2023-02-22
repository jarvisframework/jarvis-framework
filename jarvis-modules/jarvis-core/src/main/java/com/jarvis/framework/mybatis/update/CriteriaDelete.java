package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;

import java.util.function.Consumer;

/**
 * 删除条件
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月20日
 */
public class CriteriaDelete<Column> extends AbstractCriteriaCondition<Column, CriteriaDelete<Column>> {

    /**
     * 设置查询表名
     *
     * @param tableName
     * @return
     */
    @Override
    public CriteriaDelete<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * 构建检索条件
     *
     * @param function
     * @return
     */
    @Override
    public CriteriaDelete<Column> filter(BuildFunction<ComposedCondition<Column>> function) {
        function.build(this.filter);
        return this;
    }

    /**
     * 构建检索条件
     *
     * @param function
     * @return
     */
    @Override
    public CriteriaDelete<Column> filter(Consumer<ComposedCondition<Column>> function) {
        function.accept(this.filter);
        return this;
    }

    /*public static void main(String[] args) {
        CriteriaDelete.newCritiaDelete().buildCondition(condition -> {
            condition
            return condition;
        }).tableName("");
    }*/
}
