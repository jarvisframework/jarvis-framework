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
import java.util.Iterator;
import java.util.List;

public class UpgradeExecutor {
    private static Logger log = LoggerFactory.getLogger(UpgradeExecutor.class);
    private final DataSource dataSource;
    private final UpgradeDialect dialect;

    public UpgradeExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dialect = UpgradeDialectFactory.getDialect(dataSource);
    }

    public void executeDdl(DatabaseUpgrade upgrade) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.createStatement();
            List<String> sqls = UpgradeDialectUtil.toDdlSql(this.dialect, upgrade);
            conn.setAutoCommit(false);

            String sql;
            for(Iterator var5 = sqls.iterator(); var5.hasNext(); stmt.addBatch(sql)) {
                sql = (String)var5.next();
                if (log.isDebugEnabled()) {
                    log.debug("DDL脚本：{}", sql);
                }
            }

            stmt.executeBatch();
            conn.commit();
        } catch (Exception var19) {
            try {
                conn.rollback();
            } catch (SQLException var18) {
                log.error("executeDdl执行[Connection.rollback]方法出错", var18);
            }

            throw new FrameworkException(var19);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException var17) {
                    log.error("executeDdl执行[Statement.close]方法出错", var17);
                }
            }

            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException var16) {
                    log.error("executeDdl执行[Connection.close]方法出错", var16);
                }
            }

        }

    }

    public void executeSql(DatabaseUpgrade upgrade) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = this.dataSource.getConnection();
            stmt = conn.createStatement();
            List<String> sqls = upgrade.getSqls();
            conn.setAutoCommit(false);

            String sql;
            for(Iterator var5 = sqls.iterator(); var5.hasNext(); stmt.addBatch(sql)) {
                sql = (String)var5.next();
                if (log.isDebugEnabled()) {
                    log.debug("DML脚本：{}", sql);
                }
            }

            stmt.executeBatch();
            conn.commit();
        } catch (Exception var19) {
            try {
                conn.rollback();
            } catch (SQLException var18) {
                log.error("executeSql执行[Connection.rollback]方法出错", var18);
            }

            throw new FrameworkException(var19);
        } finally {
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException var17) {
                    log.error("executeSql执行[Statement.close]方法出错", var17);
                }
            }

            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException var16) {
                    log.error("executeSql执行[Connection.close]方法出错", var16);
                }
            }

        }

    }
}
