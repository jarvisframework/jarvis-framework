package com.jarvis.framework.database.upgrade.convert;

import com.jarvis.framework.database.upgrade.convert.support.DateFunctionConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DelegateFunctionConverter implements FunctionConverter {
    private static final List<FunctionConverter> converters = new ArrayList();

    public DelegateFunctionConverter() {
    }

    public String convert(String sql, String databaseId) {
        FunctionConverter converter;
        for(Iterator var3 = converters.iterator(); var3.hasNext(); sql = converter.convert(sql, databaseId)) {
            converter = (FunctionConverter)var3.next();
        }

        return sql;
    }

    public static void registConverter(List<FunctionConverter> converters) {
        DelegateFunctionConverter.converters.addAll(converters);
    }

    static {
        converters.add(new DateFunctionConverter());
    }
}
