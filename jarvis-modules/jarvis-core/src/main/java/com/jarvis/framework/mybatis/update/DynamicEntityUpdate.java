package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.mybatis.update.util.DatabaseFunctionUtil;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.ColumnLabelUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月29日
 */
public class DynamicEntityUpdate extends CriteriaUpdate<String> {

    private final Map<String, String> columnLabels = new HashMap<>();

    /**
     * 创建对象
     *
     * @param <T> 实体类型
     * @return DynamicEntityUpdate
     */
    public static DynamicEntityUpdate create() {
        return new DynamicEntityUpdate();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#update(java.lang.Object, java.lang.Object)
     */
    @Override
    public DynamicEntityUpdate update(String column, Object value) {
        super.update(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#plus(java.lang.Object, java.lang.Number)
     */
    @Override
    public DynamicEntityUpdate plus(String column, Number value) {
        super.plus(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#plus(java.lang.Object, java.lang.Object, java.lang.Number)
     */
    @Override
    public DynamicEntityUpdate plus(String column, String valueColumn, Number value) {
        super.plus(column, valueColumn, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#minus(java.lang.Object, java.lang.Number)
     */
    @Override
    public DynamicEntityUpdate minus(String column, Number value) {
        super.minus(column, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#minus(java.lang.Object, java.lang.Object, java.lang.Number)
     */
    @Override
    public DynamicEntityUpdate minus(String column, String valueColumn, Number value) {
        super.minus(column, valueColumn, value);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#concat(java.lang.Object, java.util.function.Consumer)
     */
    @Override
    public DynamicEntityUpdate concat(String column, Consumer<ConcatColumn<String>> concat) {
        super.concat(column, concat);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#tableName(java.lang.String)
     */
    @Override
    public DynamicEntityUpdate tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public DynamicEntityUpdate filter(BuildFunction<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaUpdate#filter(java.util.function.Consumer)
     */
    @Override
    public DynamicEntityUpdate filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabel 标签
     * @param columnName 字段
     * @return DynamicEntityUpdate
     */
    public DynamicEntityUpdate columnLabel(String columnLabel, String columnName) {
        //columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        columnLabels.put(columnLabel.toLowerCase(), columnName.toLowerCase());
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabels 标签
     * @return DynamicEntityUpdate
     */
    public DynamicEntityUpdate columnLabels(Map<String, String> columnLabels) {
        columnLabels.forEach((label, col) -> {
            columnLabel(label, col);
        });
        return this;
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
    }

    @SuppressWarnings("unchecked")
    private void applyColumnsColumnLabel() {
        final Map<String, Object> data = getData();
        final Iterator<Entry<String, Object>> iterator = data.entrySet().iterator();
        final Map<String, Object> labelMap = new HashMap<>();
        while (iterator.hasNext()) {
            final Entry<String, Object> entry = iterator.next();
            final String column = entry.getKey();
            final Object val = entry.getValue();
            final String col = columnLabels.get(column);
            if (null != col) {
                iterator.remove();
                labelMap.put(col, val);
            }
            if (val instanceof CalcColumn) {
                DatabaseFunctionUtil.processColumnLabel((CalcColumn) val, columnLabels);
            }
            if (val instanceof ConcatColumn) {
                DatabaseFunctionUtil.processColumnLabel((ConcatColumn<String>) val, columnLabels);
            }
            if (val instanceof FunctionValue) {
                DatabaseFunctionUtil.processColumnLabel((FunctionValue<String>) val, columnLabels);
            }
        }
        if (!labelMap.isEmpty()) {
            data.putAll(labelMap);
        }
        /*data.forEach((column, val) -> {
            final String col = columnLabels.get(column);
            if (null != col) {
                data.remove(column);
                data.put(col, val);
            }
            if (val instanceof CalcColumn) {
                DatabaseFunctionUtil.processColumnLabel((CalcColumn) val, columnLabels);
            }
            if (val instanceof ConcatColumn) {
                DatabaseFunctionUtil.processColumnLabel((ConcatColumn<String>) val, columnLabels);
            }
            if (val instanceof FunctionValue) {
                DatabaseFunctionUtil.processColumnLabel((FunctionValue<String>) val, columnLabels);
            }
        });*/
    }

    private void applyFilterColumnLabel() {
        ColumnLabelUtil.applyFilterColumnLabel(filter, columnLabels);
    }
}
