package com.jarvis.framework.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DatabaseProductNameUtil {

    public static String getDatabaseProductName(DataSource dataSource) throws SQLException {
        Connection con = null;

        String var4;
        try {
            con = dataSource.getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            String databaseId = metaData.getURL().split(":")[1].toLowerCase();
            var4 = databaseId;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException var11) {
                }
            }

        }

        return var4;
    }

    public static void main(String[] args) {
        System.out.println("kindbase8".replaceAll("\\d+", ""));
    }
}
