package com.jarvis.framework.criteria;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.function.Consumer;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月21日
 */
public abstract class AbstractCriteriaCondition<Column, Criteria extends AbstractCriteriaCondition<Column, Criteria>> {

    protected String tableName;

    protected final ComposedCondition<Column> filter = new ComposedCondition<Column>();

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 转成数据库字段
     *
     * @param column
     * @return
     */
    protected String toColumn(Column column) {
        return ColumnFunctionUtil.toDatabaseColumn(column);
    }

    /**
     * @return the filter
     */
    public ComposedCondition<Column> getFilter() {
        return filter;
    }

    /**
     * 设置查询表名
     *
     * @param tableName
     * @return
     */
    public abstract Criteria tableName(String tableName);

    /**
     * 构建检索条件
     *
     * @param function
     * @return
     */
    public abstract Criteria filter(BuildFunction<ComposedCondition<Column>> function);

    /**
     * 构建检索条件
     *
     * @param condition
     * @return
     */
    public abstract Criteria filter(Consumer<ComposedCondition<Column>> condition);

    /**
     * 应用字段标签
     */
    public void applyColumnLabel() {

    }

}
