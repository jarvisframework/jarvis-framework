package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.util.DatabaseProductNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseIdHolder {
    private static Logger log = LoggerFactory.getLogger(DatabaseIdHolder.class);
    private static String databaseId;

    public DatabaseIdHolder(DataSource dataSource) {
        try {
            databaseId = DatabaseProductNameUtil.getDatabaseProductName(dataSource);
        } catch (SQLException var3) {
            log.error("初始化databaseId失败", var3);
        }

    }

    public static String databaseId() {
        return databaseId;
    }

    public static boolean isOracle() {
        return DatabaseIdEnum.Oracle.getCode().equals(databaseId);
    }

    public static boolean isMySQL() {
        return DatabaseIdEnum.MySQL.getCode().equals(databaseId);
    }

    public static boolean isSQLServer() {
        return DatabaseIdEnum.SQLServer.getCode().equals(databaseId);
    }

    public static boolean isPostgreSQL() {
        return DatabaseIdEnum.PostgreSQL.getCode().equals(databaseId);
    }

    public static boolean isDM() {
        return DatabaseIdEnum.DM.getCode().equals(databaseId);
    }

    public static boolean isOscar() {
        return DatabaseIdEnum.Oscar.getCode().equals(databaseId);
    }

    public static boolean isKingBase() {
        return DatabaseIdEnum.KingBase.getCode().equals(databaseId);
    }

    public static boolean isKingBase8() {
        return DatabaseIdEnum.KingBase8.getCode().equals(databaseId);
    }

    public static boolean isHighgo() {
        return DatabaseIdEnum.Highgo.getCode().equals(databaseId);
    }

    public static boolean isH2() {
        return DatabaseIdEnum.H2.getCode().equals(databaseId);
    }

    public static boolean isGbase() {
        return DatabaseIdEnum.Gbase.getCode().equals(databaseId);
    }
}
