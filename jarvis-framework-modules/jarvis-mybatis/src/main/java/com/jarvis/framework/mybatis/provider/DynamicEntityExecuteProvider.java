package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.mybatis.mapping.CrudDialectFactory;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.util.Map;

public class DynamicEntityExecuteProvider implements ProviderMethodResolver {

    public static String getById(ProviderContext context, Map<String, Object> parameter) {
        String tableName = (String)parameter.get("tableName");
        return CrudDialectFactory.getDialect().getById(tableName);
    }

    public static String deleteById(ProviderContext context, Map<String, Object> parameter) {
        String tableName = (String)parameter.get("tableName");
        return CrudDialectFactory.getDialect().deleteById(tableName);
    }

    public static String deleteByIds(ProviderContext context, Map<String, Object> parameter) {
        String tableName = (String)parameter.get("tableName");
        return CrudDialectFactory.getDialect().deleteByIds(tableName);
    }
}
