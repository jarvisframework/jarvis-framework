package com.jarvis.framework.mybatis.mapping;

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

public class CrudDialectFactory {
    private static Map<String, CurdDialect> CURD_DIALECT_MAP = new HashMap();

    public CrudDialectFactory() {
    }

    public static CurdDialect getDialect() {
        String databaseId = DatabaseIdHolder.databaseId();
        CurdDialect dialect = (CurdDialect)CURD_DIALECT_MAP.get(databaseId);
        if (null == dialect) {
            throw new FrameworkException("不支持数据库[" + databaseId + "]出错");
        } else {
            return dialect;
        }
    }

    public static void registDialect(String databaseId, CurdDialect dialect) {
        CURD_DIALECT_MAP.put(databaseId, dialect);
    }

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
}
