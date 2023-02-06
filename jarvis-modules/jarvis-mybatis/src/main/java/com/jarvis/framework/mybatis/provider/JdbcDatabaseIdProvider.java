package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.util.DatabaseProductNameUtil;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 取jdbc.url里的jdbc:xxxx://中的xxxx作为databaseId（全小写），如
 * jdbc.url=jdbc:oracle://127.0.0.1:1521:orcl，则databaseId=oracle
 * jdbc.url=jdbc:oscar://127.0.0.1:2003/OSRDB，则databaseId=oscar
 *
 * @author qiucs
 * @version 1.0.0 2021年1月18日
 */
public class JdbcDatabaseIdProvider implements DatabaseIdProvider {

    private final Logger log = LoggerFactory.getLogger(JdbcDatabaseIdProvider.class);

    private Properties properties;

    @Override
    public String getDatabaseId(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource cannot be null");
        }
        try {
            return getDatabaseName(dataSource);
        } catch (final Exception e) {
            log.error("Could not get a databaseId from dataSource", e);
        }
        return null;
    }

    @Override
    public void setProperties(Properties p) {
        this.properties = p;
    }

    private String getDatabaseName(DataSource dataSource) throws SQLException {
        final String productName = getDatabaseProductName(dataSource);
        if (this.properties != null) {
            for (final Map.Entry<Object, Object> property : properties.entrySet()) {
                if (productName.contains((String) property.getKey())) {
                    return (String) property.getValue();
                }
            }
            // no match, return null
            return null;
        }
        return productName;
    }

    private String getDatabaseProductName(DataSource dataSource) throws SQLException {
        return DatabaseProductNameUtil.getDatabaseProductName(dataSource);
    }

}
