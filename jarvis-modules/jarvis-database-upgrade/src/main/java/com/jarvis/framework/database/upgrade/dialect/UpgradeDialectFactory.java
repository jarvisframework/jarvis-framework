package com.jarvis.framework.database.upgrade.dialect;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.database.upgrade.dialect.support.DmUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.H2UpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.KingbaseUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.MysqlUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.OracleUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.PostgresqlUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.support.SqlserverUpgradeDialect;
import com.jarvis.framework.util.DatabaseProductNameUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月2日
 */
public class UpgradeDialectFactory {

    private static final Map<String, UpgradeDialect> DIALECT_MAP = new HashMap<>(16);

    static {
        DIALECT_MAP.put(DatabaseIdEnum.Oracle.getCode(), OracleUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.DM.getCode(), DmUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.Oscar.getCode(), OracleUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.SQLServer.getCode(), SqlserverUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.MySQL.getCode(), MysqlUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.H2.getCode(), H2UpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.PostgreSQL.getCode(), PostgresqlUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.KingBase.getCode(), KingbaseUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.KingBase8.getCode(), KingbaseUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.Highgo.getCode(), PostgresqlUpgradeDialect.INSTANCE);
        DIALECT_MAP.put(DatabaseIdEnum.Gbase.getCode(), MysqlUpgradeDialect.INSTANCE);
    }

    public static UpgradeDialect getDialect(DataSource dataSource) {

        try {
            final String databaseId = DatabaseProductNameUtil.getDatabaseProductName(dataSource);
            return DIALECT_MAP.get(databaseId);
        } catch (final SQLException e) {
            throw new FrameworkException(e);
        }

    }

    public static UpgradeDialect getDialect(String databaseId) {
        return DIALECT_MAP.get(databaseId);
    }

    public static void registDialect(String databaseId, UpgradeDialect dialect) {
        DIALECT_MAP.put(databaseId, dialect);
    }

}
