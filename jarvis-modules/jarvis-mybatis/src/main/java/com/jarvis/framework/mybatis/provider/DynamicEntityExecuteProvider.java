package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.mybatis.mapping.CrudDialectFactory;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月19日
 */
public class DynamicEntityExecuteProvider implements ProviderMethodResolver {

    /**
     * 根据ID查询
     *
     * @param context
     * @return
     */
    public static String getById(ProviderContext context, Map<String, Object> parameter) {
        final String tableName = (String) parameter.get("tableName");
        return CrudDialectFactory.getDialect().getById(tableName);
    }

    /**
     * 根据ID删除
     *
     * @param context
     * @return
     */
    public static String deleteById(ProviderContext context, Map<String, Object> parameter) {
        final String tableName = (String) parameter.get("tableName");
        return CrudDialectFactory.getDialect().deleteById(tableName);
    }

    /**
     * 根据id批量删除
     *
     * @param context
     * @return String
     */
    public static String deleteByIds(ProviderContext context, Map<String, Object> parameter) {
        final String tableName = (String) parameter.get("tableName");
        return CrudDialectFactory.getDialect().deleteByIds(tableName);
    }
}
