package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.mapping.support.DmCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.GbaseCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.H2CurdDialect;
import com.jarvis.framework.mybatis.mapping.support.HighgoCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.KingbaseCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.MysqlCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.OracleCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.OscarCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.PostgreCurdDialect;
import com.jarvis.framework.mybatis.mapping.support.SqlserverCurdDialect;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月18日
 */
public class CrudDialectFactory {

    private static Map<String, CurdDialect> CURD_DIALECT_MAP = new HashMap<>();

    static {
        CURD_DIALECT_MAP.put(DatabaseIdEnum.MySQL.getCode(), MysqlCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.Oracle.getCode(), OracleCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.SQLServer.getCode(), SqlserverCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.DM.getCode(), DmCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.H2.getCode(), H2CurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.PostgreSQL.getCode(), PostgreCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.Oscar.getCode(), OscarCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.KingBase.getCode(), KingbaseCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.KingBase8.getCode(), KingbaseCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.Highgo.getCode(), HighgoCurdDialect.INSTANCE);
        CURD_DIALECT_MAP.put(DatabaseIdEnum.Gbase.getCode(), GbaseCurdDialect.INSTANCE);
    }

    public static CurdDialect getDialect() {
        final String databaseId = DatabaseIdHolder.databaseId();
        final CurdDialect dialect = CURD_DIALECT_MAP.get(databaseId);
        if (null == dialect) {
            throw new FrameworkException("不支持数据库[" + databaseId + "]出错");
        }

        return dialect;
    }

    /**
     * 增删改查方言注册
     *
     * @param databaseId
     * @param dialect
     */
    public static void registDialect(String databaseId, CurdDialect dialect) {
        CURD_DIALECT_MAP.put(databaseId, dialect);
    }

}
