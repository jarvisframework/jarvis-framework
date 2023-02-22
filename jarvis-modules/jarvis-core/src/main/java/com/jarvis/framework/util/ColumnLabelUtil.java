package com.jarvis.framework.util;

import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.search.ConditionExpression;
import com.jarvis.framework.search.SingleCondition;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年10月8日
 */
public class ColumnLabelUtil {

    /**
     * 把字段标签转成对应的字段
     *
     * @param filter 条件
     * @param columnLabels 标签与字段映射关系
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void applyFilterColumnLabel(ComposedCondition<String> filter, Map<String, String> columnLabels) {
        final List<ConditionExpression> expressions = filter.getConditionExpressions();
        expressions.forEach(e -> {
            if (e instanceof ComposedCondition) {
                applyFilterColumnLabel((ComposedCondition<String>) e, columnLabels);
            } else {
                final SingleCondition condition = (SingleCondition) e;
                final Object column = condition.getColumn();
                if (column instanceof String) {
                    final String val = columnLabels.get(column);
                    if (null == val) {
                        return;
                    }
                    condition.setColumn(ColumnFunctionUtil.toDatabaseColumn(val));
                } else {
                    final String[] cols = (String[]) column;
                    for (int i = 0, len = cols.length; i < len; i++) {
                        final String col = cols[i];
                        final String val = columnLabels.get(column);
                        if (null == val) {
                            continue;
                        }
                        cols[i] = ColumnFunctionUtil.toDatabaseColumn(col);
                    }
                    condition.setColumn(cols);
                }
            }
        });
    }

}
