package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.search.ComposedCondition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CriteriaUpdate<Column> extends AbstractCriteriaCondition<Column, CriteriaUpdate<Column>> {
    private final Map<String, Object> data = new HashMap(8);


    public Map<String, Object> getData() {
        return this.data;
    }

    public CriteriaUpdate<Column> update(Column column, Object value) {
        this.data.put(this.toColumn(column), value);
        return this;
    }

    public CriteriaUpdate<Column> plus(Column column, Number value) {
        String col = this.toColumn(column);
        this.data.put(col, CalcColumn.plus(col, value));
        return this;
    }

    public CriteriaUpdate<Column> plus(Column column, Column valueColumn, Number value) {
        String col = this.toColumn(column);
        String valCol = this.toColumn(valueColumn);
        this.data.put(col, CalcColumn.plus(valCol, value));
        return this;
    }

    public CriteriaUpdate<Column> minus(Column column, Number value) {
        String col = this.toColumn(column);
        this.data.put(col, CalcColumn.minus(col, value));
        return this;
    }

    public CriteriaUpdate<Column> minus(Column column, Column valueColumn, Number value) {
        String col = this.toColumn(column);
        String valCol = this.toColumn(valueColumn);
        this.data.put(col, CalcColumn.minus(valCol, value));
        return this;
    }

    public CriteriaUpdate<Column> concat(Column column, Consumer<ConcatColumn<Column>> concat) {
        String col = this.toColumn(column);
        ConcatColumn<Column> value = new ConcatColumn();
        concat.accept(value);
        this.data.put(col, value);
        return this;
    }

    public CriteriaUpdate<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CriteriaUpdate<Column> filter(BuildFunction<ComposedCondition<Column>> condition) {
        condition.build(this.filter);
        return this;
    }

    public CriteriaUpdate<Column> filter(Consumer<ComposedCondition<Column>> condition) {
        condition.accept(this.filter);
        return this;
    }
}
