package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.mybatis.update.util.DatabaseFunctionUtil;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.framework.util.ColumnLabelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DynamicEntityUpdate extends CriteriaUpdate<String> {
    private final Map<String, String> columnLabels = new HashMap();

    public DynamicEntityUpdate() {
    }

    public static DynamicEntityUpdate create() {
        return new DynamicEntityUpdate();
    }

    public DynamicEntityUpdate update(String column, Object value) {
        super.update(column, value);
        return this;
    }

    public DynamicEntityUpdate plus(String column, Number value) {
        super.plus(column, value);
        return this;
    }

    public DynamicEntityUpdate plus(String column, String valueColumn, Number value) {
        super.plus(column, valueColumn, value);
        return this;
    }

    public DynamicEntityUpdate minus(String column, Number value) {
        super.minus(column, value);
        return this;
    }

    public DynamicEntityUpdate minus(String column, String valueColumn, Number value) {
        super.minus(column, valueColumn, value);
        return this;
    }

    public DynamicEntityUpdate concat(String column, Consumer<ConcatColumn<String>> concat) {
        super.concat(column, concat);
        return this;
    }

    public DynamicEntityUpdate tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public DynamicEntityUpdate filter(BuildFunction<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    public DynamicEntityUpdate filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    public DynamicEntityUpdate columnLabel(String columnLabel, String columnName) {
        this.columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        return this;
    }

    public DynamicEntityUpdate columnLabels(Map<String, String> columnLabels) {
        columnLabels.forEach((label, col) -> {
            this.columnLabel(label, col);
        });
        return this;
    }

    public void applyColumnLabel() {
        if (!this.columnLabels.isEmpty()) {
            this.applyColumnsColumnLabel();
            this.applyFilterColumnLabel();
        }
    }

    private void applyColumnsColumnLabel() {
        Map<String, Object> data = this.getData();
        data.forEach((column, val) -> {
            String col = (String)this.columnLabels.get(column);
            if (null != col) {
                data.remove(column);
                data.put(col, val);
            }

            if (val instanceof CalcColumn) {
                DatabaseFunctionUtil.processColumnLabel((CalcColumn)val, this.columnLabels);
            }

            if (val instanceof ConcatColumn) {
                DatabaseFunctionUtil.processColumnLabel((ConcatColumn)val, this.columnLabels);
            }

            if (val instanceof FunctionValue) {
                DatabaseFunctionUtil.processColumnLabel((FunctionValue)val, this.columnLabels);
            }

        });
    }

    private void applyFilterColumnLabel() {
        ColumnLabelUtil.applyFilterColumnLabel(this.filter, this.columnLabels);
    }
}
