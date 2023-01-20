package com.jarvis.framework.util;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.function.SerializableFunction;
import org.springframework.util.StringUtils;

public class ColumnFunctionUtil {

    public static <Column> String toColumn(Column column) {
        if (String.class.isAssignableFrom(column.getClass())) {
            return (String)column;
        } else if (SerializableFunction.class.isAssignableFrom(column.getClass())) {
            return SerializableFunctionUtil.getFieldName((SerializableFunction)column);
        } else {
            throw new BusinessException("字段只能是String类型或SerializableFunction类型");
        }
    }

    public static String fieldToColumn(String field) {
        StringBuilder col = new StringBuilder(32);
        char[] arrayChar = field.toCharArray();
        char[] var3 = arrayChar;
        int var4 = arrayChar.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            if (Character.isUpperCase(c)) {
                col.append("_").append(Character.toLowerCase(c));
            } else {
                col.append(c);
            }
        }

        return col.toString();
    }

    public static <Column> String toDatabaseColumn(Column column) {
        if (String.class.isAssignableFrom(column.getClass())) {
            return (String)column;
        } else if (SerializableFunction.class.isAssignableFrom(column.getClass())) {
            String field = SerializableFunctionUtil.getFieldName((SerializableFunction)column);
            return fieldToColumn(field);
        } else {
            throw new BusinessException("字段只能是String类型或SerializableFunction类型");
        }
    }

    private static String processColumn(String column) {
        String[] cols = column.split("\\s");
        if (1 == cols.length) {
            return fieldToColumn(column);
        } else {
            cols[0] = fieldToColumn(cols[0]);
            return StringUtils.arrayToDelimitedString(cols, " ");
        }
    }
}
