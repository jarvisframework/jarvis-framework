package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.framework.util.ColumnLabelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class DynamicEntityDelete extends CriteriaDelete<String> {

    private final Map<String, String> columnLabels = new HashMap();

    public static DynamicEntityDelete create() {
        return new DynamicEntityDelete();
    }

    public DynamicEntityDelete tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public DynamicEntityDelete filter(BuildFunction<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    public DynamicEntityDelete filter(Consumer<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    public DynamicEntityDelete columnLabel(String columnLabel, String columnName) {
        this.columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        return this;
    }

    public DynamicEntityDelete columnLabels(Map<String, String> columnLabels) {
        columnLabels.forEach((label, col) -> {
            this.columnLabel(label, col);
        });
        return this;
    }

    public void applyColumnLabel() {
        if (!this.columnLabels.isEmpty()) {
            ColumnLabelUtil.applyFilterColumnLabel(this.filter, this.columnLabels);
        }
    }
}
