package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.mybatis.mapping.CrudDialectFactory;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

public class SimpleEntityExecuteProvider implements ProviderMethodResolver {
    public SimpleEntityExecuteProvider() {
    }

    public static String getById(ProviderContext context) {
        String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().getById(tableName);
    }

    public static String deleteById(ProviderContext context) {
        String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().deleteById(tableName);
    }

    public static String deleteByIds(ProviderContext context) {
        String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().deleteByIds(tableName);
    }
}
