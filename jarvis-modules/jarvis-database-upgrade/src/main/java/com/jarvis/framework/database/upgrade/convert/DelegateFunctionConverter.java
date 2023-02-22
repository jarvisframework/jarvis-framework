package com.jarvis.framework.database.upgrade.convert;

import com.jarvis.framework.database.upgrade.convert.support.DateFunctionConverter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月8日
 */
public class DelegateFunctionConverter implements FunctionConverter {

    private static final List<FunctionConverter> converters = new ArrayList<>();

    static {
        converters.add(new DateFunctionConverter());
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.convert.FunctionConverter#convert(java.lang.String, java.lang.String)
     */
    @Override
    public String convert(String sql, String databaseId) {
        for (final FunctionConverter converter : converters) {
            sql = converter.convert(sql, databaseId);
        }
        return sql;
    }

    public static void registConverter(List<FunctionConverter> converters) {
        DelegateFunctionConverter.converters.addAll(converters);
    }

}
