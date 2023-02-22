package com.jarvis.framework.mybatis.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月3日
 */
public class CamelCaseMapWrapper extends MapWrapper {

    /**
     * @param metaObject 元数据对象
     * @param map 数据
     */
    public CamelCaseMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (value instanceof Timestamp) {
            final Timestamp timestamp = (Timestamp) value;
            super.set(prop, timestamp.toLocalDateTime());
            return;
        }

        if (value instanceof Date) {
            final Date date = (Date) value;
            super.set(prop, date.toLocalDate());
            return;
        }

        if (value instanceof Time) {
            final Time time = (Time) value;
            super.set(prop, time.toLocalTime());
            return;
        }

        if (value instanceof BigDecimal) {
            final BigDecimal num = (BigDecimal) value;
            // long类型转换
            if (0 == num.scale()) {
                if (num.longValue() > Integer.MAX_VALUE) {
                    super.set(prop, num.longValue());
                } else {
                    super.set(prop, num.intValue());
                }
                return;
            }
        }

        super.set(prop, value);
    }

    /**
     *
     * @see org.apache.ibatis.reflection.wrapper.MapWrapper#findProperty(java.lang.String, boolean)
     */
    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping) {
            return toLowerCamelCase(name);
        }
        return super.findProperty(name, useCamelCaseMapping);
    }

    private String toLowerCamelCase(String name) {
        final StringBuilder sb = new StringBuilder(32);
        final int len = name.length();
        char ch;
        int underLineIndex = -9;
        for (int i = 0; i < len; i++) {
            ch = name.charAt(i);
            if ('_' == name.charAt(i)) {
                underLineIndex = i;
                continue;
            }
            if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(Character.toLowerCase(ch));
            }
        }
        return sb.toString();
    }
}
