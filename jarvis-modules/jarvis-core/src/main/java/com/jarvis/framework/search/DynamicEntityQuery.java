package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.mybatis.update.util.DatabaseFunctionUtil;
import com.jarvis.framework.util.ColumnLabelUtil;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月28日
 */
public class DynamicEntityQuery extends CriteriaQuery<String> {

    private final Map<String, String> columnLabels = new HashMap<>();

    /**
     * 创建对象
     *
     * @param <T> 实体
     * @return EntityQuery
     */
    public static DynamicEntityQuery create() {
        return new DynamicEntityQuery();
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public DynamicEntityQuery filter(BuildFunction<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#filter(java.util.function.Consumer)
     */
    @Override
    public DynamicEntityQuery filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#columns(java.lang.Object[])
     */
    @Override
    public DynamicEntityQuery columns(String... columns) {
        super.columns(columns);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#columns(java.util.Collection)
     */
    @Override
    public DynamicEntityQuery columns(Collection<String> columns) {
        super.columns(columns);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count()
     */
    @Override
    public DynamicEntityQuery count() {
        super.count();
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count(java.lang.String)
     */
    @Override
    public DynamicEntityQuery count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery countColumn(String column) {
        super.countColumn(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery countColumn(String column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery max(String column) {
        super.max(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery max(String column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery min(String column) {
        super.min(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery min(String column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery sum(String column) {
        super.sum(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery sum(String column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery avg(String column) {
        super.avg(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery avg(String column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#tableName(java.lang.String)
     */
    @Override
    public DynamicEntityQuery tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#asc(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery asc(String column) {
        super.asc(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#desc(java.lang.Object)
     */
    @Override
    public DynamicEntityQuery desc(String column) {
        super.desc(column);
        return this;
    }

    /**
     * @see com.jarvis.framework.search.CriteriaQuery#validate(java.lang.Object, java.lang.String)
     */
    @Override
    public DynamicEntityQuery validate(String column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#highlight(com.jarvis.framework.search.Highlight)
     */
    @Override
    public DynamicEntityQuery highlight(Highlight highlight) {
        super.highlight(highlight);
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabel 标签
     * @param columnName 字段
     * @return DynamicEntiyQuery
     */
    public DynamicEntityQuery columnLabel(String columnLabel, String columnName) {
        //columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        columnLabels.put(columnLabel.toLowerCase(), columnName.toLowerCase());
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabels 标签
     * @return DynamicEntiyQuery
     */
    public DynamicEntityQuery columnLabels(Map<String, String> columnLabels) {
        columnLabels.forEach((label, col) -> {
            columnLabel(label, col);
        });
        return this;
    }

    /**
     * 字段标签
     *
     * @return Map
     */
    public Map<String, String> columnLabels() {
        return columnLabels;
    }

    /**
     * @see com.jarvis.framework.criteria.AbstractCriteriaCondition#applyColumnLabel()
     */
    @Override
    public void applyColumnLabel() {
        if (columnLabels.isEmpty()) {
            return;
        }
        applyColumnsColumnLabel();
        applyFilterColumnLabel();
        applyGroupByColumnLabel();
        applyOrderByColumnLabel();
    }

    @SuppressWarnings("unchecked")
    private void applyColumnsColumnLabel() {
        final Set<Object> columns = getColumns();
        final Set<Object> newColumns = new LinkedHashSet<>(columns.size());
        boolean hasLabel = false;
        for (final Object column : columns) {
            if (column instanceof String) {
                final String val = columnLabels.get(column);
                if (null != val) {
                    newColumns.add(val);
                    hasLabel = true;
                } else {
                    newColumns.add(column);
                }
            } else if (column instanceof FunctionValue) {
                final FunctionValue<String> fv = (FunctionValue<String>) column;
                DatabaseFunctionUtil.processColumnLabel(fv, columnLabels);
                if (!StringUtils.hasText(fv.getAlias())) {
                    fv.alias(fv.getName());
                }
                newColumns.add(fv);
            }
        }
        if (hasLabel) {
            columns.clear();
            columns.addAll(newColumns);
        }
        newColumns.clear();
    }

    private void applyFilterColumnLabel() {
        ColumnLabelUtil.applyFilterColumnLabel(filter, columnLabels);
    }

    private void applyGroupByColumnLabel() {

        final List<String> groupBy = getGroupBy();

        for (int i = 0, len = groupBy.size(); i < len; i++) {
            final String column = groupBy.get(i);
            final String val = columnLabels.get(column);
            if (null == val) {
                continue;
            }
            groupBy.set(i, val);
        }

    }

    private void applyOrderByColumnLabel() {

        final List<OrderBy> orders = getOrders();

        for (final OrderBy orderBy : orders) {
            final String val = columnLabels.get(orderBy.getColumn());
            if (null == val) {
                continue;
            }
            orderBy.setColumn(val);
        }

    }

}
