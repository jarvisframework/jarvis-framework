package com.jarvis.framework.mybatis.plugin.page;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import com.jarvis.framework.mybatis.plugin.page.support.MysqlPageDialect;
import com.jarvis.framework.mybatis.plugin.page.support.OraclePageDialect;
import com.jarvis.framework.mybatis.plugin.page.support.SqlserverPageDialect;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年3月19日
 */
public class PageDialectFactory {

    private static final Map<String, PageDialect> PAGE_DIALECT_MAP = new HashMap<>(16);

    static {
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.Oracle.getCode(), OraclePageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.MySQL.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.SQLServer.getCode(), SqlserverPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.DM.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.KingBase.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.KingBase8.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.H2.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.Highgo.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.Oscar.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.PostgreSQL.getCode(), MysqlPageDialect.INSTANCE);
        PAGE_DIALECT_MAP.put(DatabaseIdEnum.Gbase.getCode(), MysqlPageDialect.INSTANCE);
    }

    /**
     * 注册分页方言
     *
     * @param databaseId
     * @param dialect
     */
    public static void registDialect(String databaseId, PageDialect dialect) {
        PAGE_DIALECT_MAP.put(databaseId, dialect);
    }

    /**
     * 获取分页方言
     *
     * @param databaseId
     * @return
     */
    public static PageDialect getDialect() {

        final String databaseId = DatabaseIdHolder.databaseId();

        final PageDialect dialect = PAGE_DIALECT_MAP.get(databaseId);

        if (null == dialect) {
            throw new FrameworkException("数据库[" + databaseId + "]方言不存在，无法分页");
        }

        return dialect;
    }
}
