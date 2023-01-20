package com.jarvis.framework.util;

import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.search.ConditionExpression;
import com.jarvis.framework.search.SingleCondition;

import java.util.List;
import java.util.Map;

public class ColumnLabelUtil {

    public static void applyFilterColumnLabel(ComposedCondition<String> filter, Map<String, String> columnLabels) {
        List<ConditionExpression> expressions = filter.getConditionExpressions();
        expressions.forEach((e) -> {
            if (e instanceof ComposedCondition) {
                applyFilterColumnLabel((ComposedCondition)e, columnLabels);
            } else {
                SingleCondition condition = (SingleCondition)e;
                Object column = condition.getColumn();
                if (column instanceof String) {
                    String valx = (String)columnLabels.get(column);
                    if (null == valx) {
                        return;
                    }

                    condition.setColumn(ColumnFunctionUtil.toDatabaseColumn(valx));
                } else {
                    String[] cols = (String[])((String[])column);
                    int i = 0;

                    for(int len = cols.length; i < len; ++i) {
                        String col = cols[i];
                        String val = (String)columnLabels.get(column);
                        if (null != val) {
                            cols[i] = ColumnFunctionUtil.toDatabaseColumn(col);
                        }
                    }

                    condition.setColumn(cols);
                }
            }

        });
    }
}
