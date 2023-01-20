package com.jarvis.framework.mybatis.update.util;

import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ColumnValue;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;

public class DatabaseFunctionUtil {

    public static <T> void processColumnLabel(FunctionValue<T> fun, Map<String, String> columnLabels) {
        fun.getParams().forEach((e) -> {
            if (!e.isValue()) {
                Object value = e.getValue();
                String column = columnLabels.get(value);
                if (null != value) {
                    e.setValue(column);
                }

            }
        });
    }

    public static void processColumnLabel(CalcColumn fun, Map<String, String> columnLabels) {
        String column = columnLabels.get(fun.getColumn());
        if (null != column) {
            fun.setColumn(column);
        }

    }

    public static <T> void processColumnLabel(ConcatColumn<T> fun, Map<String, String> columnLabels) {
        fun.getData().forEach((e) -> {
            if (!e.isValue()) {
                Object value = e.getValue();
                String column = columnLabels.get(value);
                if (null != value) {
                    e.setValue(column);
                }

            }
        });
    }

    public static <T> String toFunctionValue(FunctionValue<T> fun, boolean appendAlias) {
        StringBuilder sb = new StringBuilder(fun.getName());
        sb.append("(");

        for (Iterator var4 = fun.getParams().iterator(); var4.hasNext(); sb.append(",")) {
            ColumnValue cv = (ColumnValue) var4.next();
            Object value = cv.getValue();
            if (cv.isValue()) {
                if (value instanceof String) {
                    sb.append("'").append(value).append("'");
                } else {
                    sb.append(value);
                }
            } else {
                sb.append(value);
            }
        }

        int start = sb.length() - 1;
        sb.replace(start, start + 1, ")");
        if (appendAlias && StringUtils.hasText(fun.getAlias())) {
            sb.append(" AS ").append(fun.getAlias());
        }

        return sb.toString();
    }
}
