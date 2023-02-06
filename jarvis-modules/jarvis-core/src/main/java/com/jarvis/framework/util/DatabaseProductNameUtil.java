package com.jarvis.framework.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月18日
 */
public class DatabaseProductNameUtil {

    public static String getDatabaseProductName(DataSource dataSource) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            final DatabaseMetaData metaData = con.getMetaData();
            final String databaseId = metaData.getURL().split(":")[1].toLowerCase();
            return databaseId;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (final SQLException e) {
                    // ignored
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("kindbase8".replaceAll("\\d+", ""));
    }
}
