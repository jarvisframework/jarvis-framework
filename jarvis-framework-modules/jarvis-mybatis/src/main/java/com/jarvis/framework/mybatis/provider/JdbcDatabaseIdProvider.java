package com.jarvis.framework.mybatis.provider;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDatabaseIdProvider implements DatabaseIdProvider {

    private final Logger log = LoggerFactory.getLogger(JdbcDatabaseIdProvider.class);

    private Properties properties;

    public JdbcDatabaseIdProvider() {
    }

    public String getDatabaseId(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource cannot be null");
        } else {
            try {
                return this.getDatabaseName(dataSource);
            } catch (Exception var3) {
                this.log.error("Could not get a databaseId from dataSource", var3);
                return null;
            }
        }
    }

    public void setProperties(Properties p) {
        this.properties = p;
    }

    private String getDatabaseName(DataSource dataSource) throws SQLException {
        String productName = this.getDatabaseProductName(dataSource);
        if (this.properties != null) {
            Iterator var3 = this.properties.entrySet().iterator();

            Entry property;
            do {
                if (!var3.hasNext()) {
                    return null;
                }

                property = (Entry) var3.next();
            } while (!productName.contains((String) property.getKey()));

            return (String) property.getValue();
        } else {
            return productName;
        }
    }

    private String getDatabaseProductName(DataSource dataSource) throws SQLException {
        return DatabaseProductNameUtil.getDatabaseProductName(dataSource);
    }
}
