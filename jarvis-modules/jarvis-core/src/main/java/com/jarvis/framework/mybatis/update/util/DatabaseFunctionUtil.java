package com.jarvis.framework.mybatis.update.util;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.CaseWhen;
import com.jarvis.framework.mybatis.update.data.ColumnValue;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月12日
 */
public class DatabaseFunctionUtil {

    /**
     * 处理函数里字段
     *
     * @param <T> 泛型
     * @param fun 函数
     * @param columnLabels 字段标签
     */
    public static <T> void processColumnLabel(FunctionValue<T> fun, Map<String, String> columnLabels) {
        fun.getParams().forEach(e -> {
            if (e.isValue()) {
                return;
            }
            final Object value = e.getValue();
            final String column = columnLabels.get(value);
            if (null != column) {
                e.setValue(column);
            }
        });
    }

    /**
     * 处理计算函数里字段
     *
     * @param fun 函数
     * @param columnLabels 字段标签
     */
    public static void processColumnLabel(CalcColumn fun, Map<String, String> columnLabels) {
        final String column = columnLabels.get(fun.getColumn());
        if (null != column) {
            fun.setColumn(column);
        }
    }

    /**
     * 处理case when里字段
     *
     * @param cw 函数
     * @param columnLabels 字段标签
     */
    public static <T> void processColumnLabel(CaseWhen<T> cw, Map<String, String> columnLabels) {
        String column = columnLabels.get(cw.getColumn());
        if (null != column) {
            cw.setColumn(column);
        }
        if (null == cw.getOther()) {
            return;
        }
        final ColumnValue cv = cw.getOther();
        if (cv.isValuable()) {
            return;
        }
        column = columnLabels.get(cw.getOther().getValue());
        if (null != column) {
            cv.setValue(column);
        }
    }

    /**
     * 处理拼接函数里字段
     *
     * @param <T> 泛型
     * @param fun 函数
     * @param columnLabels 字段标签
     */
    public static <T> void processColumnLabel(ConcatColumn<T> fun, Map<String, String> columnLabels) {
        fun.getData().forEach(e -> {
            if (e instanceof ColumnValue) {
                final ColumnValue cv = (ColumnValue) e;
                if (cv.isValue()) {
                    return;
                }
                final Object value = cv.getValue();
                final String column = columnLabels.get(value);
                if (null != column) {
                    cv.setValue(column);
                }
            } else if (e instanceof FunctionValue) {
                final FunctionValue<?> func = (FunctionValue<?>) e;
                processColumnLabel(func, columnLabels);
            } else if (e instanceof CaseWhen) {
                final CaseWhen<?> cw = (CaseWhen<?>) e;
                processColumnLabel(cw, columnLabels);
            }
        });
    }

    /**
     * 解析函数
     *
     * @param <T> 泛型
     * @param fun 函数
     */
    public static <T> String toFunctionValue(FunctionValue<T> fun, boolean appendAlias) {
        final StringBuilder sb = new StringBuilder(fun.getName());
        sb.append(SymbolConstant.OPEN_PARENTHESIS);
        Object value;
        for (final ColumnValue cv : fun.getParams()) {
            value = cv.getValue();
            if (cv.isValue()) {
                sb.append(toValue(cv.getValue()));
            } else {
                sb.append(value);
            }
            sb.append(", ");
        }

        final int length = sb.length();
        final int start = length - 2;
        sb.replace(start, length, SymbolConstant.CLOSE_PARENTHESIS);

        if (appendAlias && StringUtils.hasText(fun.getAlias())) {
            sb.append(" AS ").append(fun.getAlias());
        }
        return sb.toString();
    }

    /**
     * 解析 case when
     *
     * @param <T> 泛型
     * @param cw CaseWhen对象
     */
    public static <T> String toCaseWhen(CaseWhen<T> cw, boolean appendAlias) {
        final StringBuilder sb = new StringBuilder();
        sb.append(SymbolConstant.OPEN_PARENTHESIS)
                .append("CASE ").append(cw.getColumn());

        cw.getWhenMap().forEach((key, val) -> {
            sb.append(" WHEN ").append(toValue(key)).append(" THEN ").append(toValue(val));
        });

        if (null != cw.getOther()) {
            if (cw.getOther().isValue()) {
                sb.append(" ELSE ").append(toValue(cw.getOther().getValue()));
            } else {
                sb.append(" ELSE ").append(cw.getOther().getValue());
            }
        }

        sb.append(" END");

        sb.append(SymbolConstant.CLOSE_PARENTHESIS);

        if (appendAlias && StringUtils.hasText(cw.getAlias())) {
            sb.append(" AS ").append(cw.getAlias());
        }
        return sb.toString();
    }

    /**
     * 值转
     *
     * @param val
     * @return
     */
    public static String toValue(Object val) {
        if (val instanceof String) {
            return SymbolConstant.SINGLE_QUOTES + val + SymbolConstant.SINGLE_QUOTES;
        }
        return String.valueOf(val);
    }

}
