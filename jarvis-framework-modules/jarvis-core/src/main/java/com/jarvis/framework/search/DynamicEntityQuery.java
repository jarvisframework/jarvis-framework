package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.framework.util.ColumnLabelUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class DynamicEntityQuery extends CriteriaQuery<String> {

    private final Map<String, String> columnLabels = new HashMap();

    public static DynamicEntityQuery create() {
        return new DynamicEntityQuery();
    }

    public DynamicEntityQuery filter(BuildFunction<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    public DynamicEntityQuery filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    public DynamicEntityQuery columns(String... columns) {
        super.columns(columns);
        return this;
    }

    public DynamicEntityQuery columns(Collection<String> columns) {
        super.columns(columns);
        return this;
    }

    public DynamicEntityQuery count() {
        super.count();
        return this;
    }

    public DynamicEntityQuery count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    public DynamicEntityQuery countColumn(String column) {
        super.countColumn(column);
        return this;
    }

    public DynamicEntityQuery countColumn(String column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    public DynamicEntityQuery max(String column) {
        super.max(column);
        return this;
    }

    public DynamicEntityQuery max(String column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    public DynamicEntityQuery min(String column) {
        super.min(column);
        return this;
    }

    public DynamicEntityQuery min(String column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    public DynamicEntityQuery sum(String column) {
        super.sum(column);
        return this;
    }

    public DynamicEntityQuery sum(String column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    public DynamicEntityQuery avg(String column) {
        super.avg(column);
        return this;
    }

    public DynamicEntityQuery avg(String column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    public DynamicEntityQuery tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public DynamicEntityQuery asc(String column) {
        super.asc(column);
        return this;
    }

    public DynamicEntityQuery desc(String column) {
        super.desc(column);
        return this;
    }

    public DynamicEntityQuery validate(String column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }

    public DynamicEntityQuery columnLabel(String columnLabel, String columnName) {
        this.columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        return this;
    }

    public DynamicEntityQuery columnLabels(Map<String, String> columnLabels) {
        columnLabels.forEach((label, col) -> {
            this.columnLabel(label, col);
        });
        return this;
    }

    public Map<String, String> columnLabels() {
        return this.columnLabels;
    }

    public void applyColumnLabel() {
        if (!this.columnLabels.isEmpty()) {
            this.applyColumnsColumnLabel();
            this.applyFilterColumnLabel();
            this.applyGroupByColumnLabel();
            this.applyOrderByColumnLabel();
        }
    }

    private void applyColumnsColumnLabel() {
        Set<String> columns = this.getColumns();
        Set<String> newColumns = new LinkedHashSet(columns.size());
        boolean hasLabel = false;
        Iterator var4 = columns.iterator();

        while(var4.hasNext()) {
            String column = (String)var4.next();
            String val = (String)this.columnLabels.get(column);
            if (null != val) {
                newColumns.add(val);
                hasLabel = true;
            } else {
                newColumns.add(column);
            }
        }

        if (hasLabel) {
            columns.clear();
            columns.addAll(newColumns);
        }

        newColumns.clear();
    }

    private void applyFilterColumnLabel() {
        ColumnLabelUtil.applyFilterColumnLabel(this.filter, this.columnLabels);
    }

    private void applyGroupByColumnLabel() {
        List<String> groupBy = this.getGroupBy();
        int i = 0;

        for(int len = groupBy.size(); i < len; ++i) {
            String column = (String)groupBy.get(i);
            String val = (String)this.columnLabels.get(column);
            if (null != val) {
                groupBy.set(i, val);
            }
        }

    }

    private void applyOrderByColumnLabel() {
        List<OrderBy> orders = this.getOrders();
        Iterator var2 = orders.iterator();

        while(var2.hasNext()) {
            OrderBy orderBy = (OrderBy)var2.next();
            String val = (String)this.columnLabels.get(orderBy.getColumn());
            if (null != val) {
                orderBy.setColumn(val);
            }
        }

    }
}
