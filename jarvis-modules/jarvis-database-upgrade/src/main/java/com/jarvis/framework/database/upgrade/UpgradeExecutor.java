package com.jarvis.framework.database.upgrade;

import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialectFactory;
import com.jarvis.framework.database.upgrade.model.DatabaseUpgrade;
import com.jarvis.framework.database.upgrade.util.UpgradeDialectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月6日
 */
public class UpgradeExecutor {

    private static Logger log = LoggerFactory.getLogger(UpgradeExecutor.class);

    private final DataSource dataSource;

    private final UpgradeDialect dialect;

    /**
     * @param dataSource
     */
    public UpgradeExecutor(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
        dialect = UpgradeDialectFactory.getDialect(dataSource);
    }

    /**
     * 执行DDL语句
     *
     * @param upgrade
     */
    public void executeDdl(DatabaseUpgrade upgrade) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            final List<String> sqls = UpgradeDialectUtil.toDdlSql(dialect, upgrade);

            conn.setAutoCommit(false);
            for (final String sql : sqls) {
                if (log.isDebugEnabled()) {
                    log.debug("DDL脚本：{}", sql);
                }
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            try {
                conn.rollback();
            } catch (final SQLException e1) {
                log.error("executeDdl执行[Connection.rollback]方法出错", e1);
            }
            throw new FrameworkException(e);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (final SQLException e) {
                    log.error("executeDdl执行[Statement.close]方法出错", e);
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (final SQLException e) {
                    log.error("executeDdl执行[Connection.close]方法出错", e);
                }
            }
        }
    }

    /**
     * 执行SQL语句
     *
     * @param upgrade
     */
    public void executeSql(DatabaseUpgrade upgrade) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();

            final List<String> sqls = upgrade.getSqls();

            conn.setAutoCommit(false);
            for (final String sql : sqls) {
                if (log.isDebugEnabled()) {
                    log.debug("DML脚本：{}", sql);
                }
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            try {
                conn.rollback();
            } catch (final SQLException e1) {
                log.error("executeSql执行[Connection.rollback]方法出错", e1);
            }
            throw new FrameworkException(e);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (final SQLException e) {
                    log.error("executeSql执行[Statement.close]方法出错", e);
                }
            }
            if (null != conn) {
                try {
                    conn.close();
                } catch (final SQLException e) {
                    log.error("executeSql执行[Connection.close]方法出错", e);
                }
            }
        }
    }

}
