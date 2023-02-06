package com.jarvis.framework.util;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.function.SerializableFunction;
import org.springframework.util.StringUtils;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月22日
 */
public class ColumnFunctionUtil {

    public static <Column> String toColumn(Column column) {
        if (String.class.isAssignableFrom(column.getClass())) {
            return (String) column;
        }
        if (SerializableFunction.class.isAssignableFrom(column.getClass())) {
            return SerializableFunctionUtil.getFieldName((SerializableFunction<?, ?>) column);
        }
        throw new BusinessException("字段只能是String类型或SerializableFunction类型");
    }

    /**
     * Java属性转字段：showName => show_name
     *
     * @param field Java属性
     * @return 字段名称
     */
    public static String fieldToColumn(String field) {
        final StringBuilder col = new StringBuilder(32);
        final char[] arrayChar = field.toCharArray();
        for (final char c : arrayChar) {
            if (Character.isUpperCase(c)) {
                col.append(SymbolConstant.UNDER_LINE).append(Character.toLowerCase(c));
            } else {
                col.append(c);
            }
        }

        return col.toString();
    }

    /**
     * 如果是字符串，则为不转换；如果get方法，则转成数据库字段，如User::getShowName => show_name
     *
     * @param <Column>
     * @param column
     * @return
     */
    public static <Column> String toDatabaseColumn(Column column) {
        if (String.class.isAssignableFrom(column.getClass())) {
            return processColumn((String) column);
            // return (String) column;
        }
        if (SerializableFunction.class.isAssignableFrom(column.getClass())) {
            final String field = SerializableFunctionUtil.getFieldName((SerializableFunction<?, ?>) column);
            return fieldToColumn(field);
        }
        throw new BusinessException("字段只能是String类型或SerializableFunction类型");
    }

    private static String processColumn(String column) {
        final String[] cols = column.split("\\s");
        if (1 == cols.length) {
            return fieldToColumn(column);
        }
        cols[0] = fieldToColumn(cols[0]);
        String col;
        for (int i = 0, len = cols.length; i < len; i++) {
            col = cols[i];
            if ("as".equalsIgnoreCase(col)) {
                continue;
            }
            cols[i] = fieldToColumn(col);
        }
        return StringUtils.arrayToDelimitedString(cols, " ");
    }
}
