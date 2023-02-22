package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.mybatis.mapping.CrudDialectFactory;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月21日
 */
public class SimpleEntityExecuteProvider implements ProviderMethodResolver {

    /**
     * 根据ID查询
     *
     * @param context
     * @return
     */
    public static String getById(ProviderContext context) {
        final String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().getById(tableName);
    }

    /**
     * 根据ID删除
     *
     * @param context
     * @return
     */
    public static String deleteById(ProviderContext context) {
        final String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().deleteById(tableName);
    }

    /**
     * 根据id批量删除
     *
     * @param context
     * @return
     */
    public static String deleteByIds(ProviderContext context) {
        final String tableName = PersistentUtil.getTableName(context);
        return CrudDialectFactory.getDialect().deleteByIds(tableName);
    }
}
