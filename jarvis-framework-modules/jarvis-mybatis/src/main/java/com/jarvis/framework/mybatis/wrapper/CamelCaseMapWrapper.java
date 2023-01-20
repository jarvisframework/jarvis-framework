package com.jarvis.framework.mybatis.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.MapWrapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

public class CamelCaseMapWrapper extends MapWrapper {

    public CamelCaseMapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject, map);
    }

    public void set(PropertyTokenizer prop, Object value) {
        if (value instanceof Timestamp) {
            Timestamp timestamp = (Timestamp)value;
            super.set(prop, timestamp.toLocalDateTime());
        } else if (value instanceof Date) {
            Date date = (Date)value;
            super.set(prop, date.toLocalDate());
        } else if (value instanceof Time) {
            Time time = (Time)value;
            super.set(prop, time.toLocalTime());
        } else {
            if (value instanceof BigDecimal) {
                BigDecimal num = (BigDecimal)value;
                if (0 == num.scale()) {
                    if (num.longValue() > 2147483647L) {
                        super.set(prop, num.longValue());
                    } else {
                        super.set(prop, num.intValue());
                    }

                    return;
                }
            }

            super.set(prop, value);
        }
    }

    public String findProperty(String name, boolean useCamelCaseMapping) {
        return useCamelCaseMapping ? this.toLowerCamelCase(name) : super.findProperty(name, useCamelCaseMapping);
    }

    private String toLowerCamelCase(String name) {
        StringBuilder sb = new StringBuilder(32);
        int len = name.length();
        int underLineIndex = -9;

        for(int i = 0; i < len; ++i) {
            char ch = name.charAt(i);
            if ('_' == name.charAt(i)) {
                underLineIndex = i;
            } else if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(Character.toLowerCase(ch));
            }
        }

        return sb.toString();
    }
}
