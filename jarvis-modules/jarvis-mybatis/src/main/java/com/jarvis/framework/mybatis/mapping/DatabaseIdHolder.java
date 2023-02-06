package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.constant.DatabaseIdEnum;
import com.jarvis.framework.util.DatabaseProductNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月18日
 */
public class DatabaseIdHolder {

    private static Logger log = LoggerFactory.getLogger(DatabaseIdHolder.class);

    private static String databaseId;

    public DatabaseIdHolder(DataSource dataSource) {
        try {
            databaseId = DatabaseProductNameUtil.getDatabaseProductName(dataSource);

        } catch (final SQLException e) {
            // ignore
            log.error("初始化databaseId失败", e);
        }
    }

    /**
     * 获取databaseId值
     *
     * @return String
     */
    public static String databaseId() {
        return databaseId;
    }

    /**
     * 是否oracle数据库
     *
     * @return boolean
     */
    public static boolean isOracle() {
        return DatabaseIdEnum.Oracle.getCode().equals(databaseId);
    }

    /**
     * 是否mysql数据库
     *
     * @return boolean
     */
    public static boolean isMySQL() {
        return DatabaseIdEnum.MySQL.getCode().equals(databaseId);
    }

    /**
     * 是否sqlserver数据库
     *
     * @return boolean
     */
    public static boolean isSQLServer() {
        return DatabaseIdEnum.SQLServer.getCode().equals(databaseId);
    }

    /**
     * 是否postgresql数据库
     *
     * @return boolean
     */
    public static boolean isPostgreSQL() {
        return DatabaseIdEnum.PostgreSQL.getCode().equals(databaseId);
    }

    /**
     * 是否达梦数据库
     *
     * @return boolean
     */
    public static boolean isDM() {
        return DatabaseIdEnum.DM.getCode().equals(databaseId);
    }

    /**
     * 是否神通数据库
     *
     * @return boolean
     */
    public static boolean isOscar() {
        return DatabaseIdEnum.Oscar.getCode().equals(databaseId);
    }

    /**
     * 是否人大金仓数据库
     *
     * @return boolean
     */
    public static boolean isKingBase() {
        return DatabaseIdEnum.KingBase.getCode().equals(databaseId);
    }

    /**
     * 是否人大金仓数据库
     *
     * @return boolean
     */
    public static boolean isKingBase8() {
        return DatabaseIdEnum.KingBase8.getCode().equals(databaseId);
    }

    /**
     * 是否翰高数据库
     *
     * @return boolean
     */
    public static boolean isHighgo() {
        return DatabaseIdEnum.Highgo.getCode().equals(databaseId);
    }

    /**
     * 是否H2数据库
     *
     * @return boolean
     */
    public static boolean isH2() {
        return DatabaseIdEnum.H2.getCode().equals(databaseId);
    }

    /**
     * 是否南大通用数据库
     *
     * @return boolean
     */
    public static boolean isGbase() {
        return DatabaseIdEnum.Gbase.getCode().equals(databaseId);
    }

}
