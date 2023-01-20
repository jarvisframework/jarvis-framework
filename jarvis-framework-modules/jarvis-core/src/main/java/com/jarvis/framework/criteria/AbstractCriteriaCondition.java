package com.jarvis.framework.criteria;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.function.Consumer;

/**
 * 抽象标准条件
 *
 * @param <Column>
 * @param <Criteria>
 */
public abstract class AbstractCriteriaCondition<Column, Criteria extends AbstractCriteriaCondition<Column, Criteria>> {
    protected String tableName;
    protected final ComposedCondition<Column> filter = new ComposedCondition();

    public AbstractCriteriaCondition() {
    }

    public String getTableName() {
        return this.tableName;
    }

    protected String toColumn(Column column) {
        return ColumnFunctionUtil.toDatabaseColumn(column);
    }

    public ComposedCondition<Column> getFilter() {
        return this.filter;
    }

    public abstract Criteria tableName(String var1);

    public abstract Criteria filter(BuildFunction<ComposedCondition<Column>> var1);

    public abstract Criteria filter(Consumer<ComposedCondition<Column>> var1);

    public void applyColumnLabel() {
    }
}
