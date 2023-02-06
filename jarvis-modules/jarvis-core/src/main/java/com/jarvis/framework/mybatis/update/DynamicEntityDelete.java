package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.util.ColumnLabelUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月29日
 */
public class DynamicEntityDelete extends CriteriaDelete<String> {

    private final Map<String, String> columnLabels = new HashMap<>();

    /**
     * 创建对象
     *
     * @param <T> 实体类型
     * @return DynamicEntityDelete
     */
    public static DynamicEntityDelete create() {
        return new DynamicEntityDelete();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#tableName(java.lang.String)
     */
    @Override
    public DynamicEntityDelete tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#filter(com.gdda.archives.framework.function.BuildFunction)
     */
    @Override
    public DynamicEntityDelete filter(BuildFunction<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.update.CriteriaDelete#filter(java.util.function.Consumer)
     */
    @Override
    public DynamicEntityDelete filter(Consumer<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabel 标签
     * @param columnName 字段
     * @return DynamicEntityDelete
     */
    public DynamicEntityDelete columnLabel(String columnLabel, String columnName) {
        //columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), columnName);
        columnLabels.put(columnLabel.toLowerCase(), columnName.toLowerCase());
        return this;
    }

    /**
     * 添加标签与字段对应关系
     *
     * @param columnLabels 标签
     * @return DynamicEntityDelete
     */
    public DynamicEntityDelete columnLabels(Map<String, String> columnLabels) {
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
        ColumnLabelUtil.applyFilterColumnLabel(filter, columnLabels);
    }

}
