package com.jarvis.framework.database.upgrade.runner;

import com.jarvis.framework.builder.BeanBuilder;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.database.upgrade.config.UpgradeProperties;
import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.convert.DelegateFunctionConverter;
import com.jarvis.framework.database.upgrade.convert.FunctionConverter;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialectFactory;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.CreateTable;
import com.jarvis.framework.database.upgrade.model.DatabaseUpgrade;
import com.jarvis.framework.database.upgrade.model.UpgradeVersion;
import com.jarvis.framework.database.upgrade.util.UpgradeDialectUtil;
import com.jarvis.framework.util.DatabaseProductNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UpgradeProcessorApplicationRunner implements ApplicationRunner, Ordered {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final DataSource dataSource;
    private final String databaseId;
    private final UpgradeProperties properties;
    private final UpgradeDialect dialect;
    private final UpgradeVersion version;
    private final String selectVersionSql = "SELECT id, version FROM sys_upgrade_version WHERE id between 1 and 2";
    private final String updateDdlVersionSql = "UPDATE sys_upgrade_version SET version=%d WHERE id=1";
    private final String updateDmlVersionSql = "UPDATE sys_upgrade_version SET version=%d WHERE id=2";
    private final String initVersionSql = "INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')";

    public UpgradeProcessorApplicationRunner(DataSource dataSource, UpgradeProperties properties) throws SQLException {
        this.dataSource = dataSource;
        this.databaseId = DatabaseProductNameUtil.getDatabaseProductName(dataSource);
        this.properties = properties;
        this.dialect = UpgradeDialectFactory.getDialect(this.databaseId);
        this.version = this.getVersion();
    }

    public int getOrder() {
        return -2147483648;
    }

    public void run(ApplicationArguments args) throws Exception {
        if (this.log.isInfoEnabled()) {
            this.log.info("开始执行数据库脚本升级......");
        }

        Optional.ofNullable(this.properties).map((properties) -> {
            return properties.getDatabaseUpgrades();
        }).ifPresent((upgrades) -> {
            upgrades.forEach((upgrade) -> {
                int version = upgrade.getVersion();
                if (version > this.version.getDdlVersion()) {
                    this.doUpgradeDdl(upgrade);
                }

                if (version > this.version.getDmlVersion()) {
                    this.doUpgradeSql(upgrade);
                }

            });
        });
        if (this.log.isInfoEnabled()) {
            this.log.info("完成执行数据库脚本升级......");
        }

    }

    private UpgradeVersion getVersion() {
        UpgradeVersion version = new UpgradeVersion();

        try {
            Connection conn = this.dataSource.getConnection();
            Throwable var3 = null;

            try {
                Statement stmt = conn.createStatement();
                Throwable var5 = null;

                try {
                    ResultSet rs = stmt.executeQuery("SELECT id, version FROM sys_upgrade_version WHERE id between 1 and 2");
                    Throwable var7 = null;

                    try {
                        while(rs.next()) {
                            if (1 == rs.getInt(1)) {
                                version.setDdlVersion(rs.getInt(2));
                            } else {
                                version.setDmlVersion(rs.getInt(2));
                            }
                        }

                        this.initVersion(conn, version);
                    } catch (Throwable var54) {
                        var7 = var54;
                        throw var54;
                    } finally {
                        if (rs != null) {
                            if (var7 != null) {
                                try {
                                    rs.close();
                                } catch (Throwable var53) {
                                    var7.addSuppressed(var53);
                                }
                            } else {
                                rs.close();
                            }
                        }

                    }
                } catch (Throwable var56) {
                    var5 = var56;
                    throw var56;
                } finally {
                    if (stmt != null) {
                        if (var5 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var52) {
                                var5.addSuppressed(var52);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Throwable var58) {
                var3 = var58;
                throw var58;
            } finally {
                if (conn != null) {
                    if (var3 != null) {
                        try {
                            conn.close();
                        } catch (Throwable var51) {
                            var3.addSuppressed(var51);
                        }
                    } else {
                        conn.close();
                    }
                }

            }
        } catch (Exception var60) {
            if (var60 instanceof FrameworkException) {
                throw (FrameworkException)var60;
            }

            this.createVersionTable();
        }

        if (this.log.isInfoEnabled()) {
            this.log.info("当前版本ddl_version={}, dml_version={}", version.getDdlVersion(), version.getDmlVersion());
        }

        return version;
    }

    private void initVersion(Connection conn, UpgradeVersion version) {
        boolean isInitDdl = -1 == version.getDdlVersion();
        boolean isInitDml = -1 == version.getDmlVersion();
        if (isInitDdl || isInitDml) {
            try {
                Statement stmt = conn.createStatement();
                Throwable var6 = null;

                try {
                    conn.setAutoCommit(false);
                    if (isInitDdl) {
                        stmt.addBatch(String.format("INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')", 1, 0, "ddl版本号"));
                    }

                    if (isInitDml) {
                        stmt.addBatch(String.format("INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')", 2, 0, "dml版本号"));
                    }

                    stmt.executeBatch();
                    conn.commit();
                } catch (Throwable var16) {
                    var6 = var16;
                    throw var16;
                } finally {
                    if (stmt != null) {
                        if (var6 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var15) {
                                var6.addSuppressed(var15);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Exception var18) {
                throw new FrameworkException("初始化数据库更新脚本的版本号出错", var18);
            }

            if (isInitDdl) {
                if (this.log.isErrorEnabled()) {
                    this.log.error("ddl版本号在表[sys_upgrade_version]中不存在，程序已恢复最初版本号；如有问题，请手动修正！");
                }

                version.setDdlVersion(0);
            }

            if (isInitDml) {
                if (this.log.isErrorEnabled()) {
                    this.log.error("dml版本号在表[sys_upgrade_version]中不存在，程序已恢复最初版本号；如有问题，请手动修正！");
                }

                version.setDmlVersion(0);
            }

        }
    }

    private void createVersionTable() {
        if (this.log.isInfoEnabled()) {
            this.log.info("初始化sys_upgrade_version表结构");
        }

        try {
            Connection conn = this.dataSource.getConnection();
            Throwable var2 = null;

            try {
                Statement stmt = conn.createStatement();
                Throwable var4 = null;

                try {
                    List<String> sqls = new ArrayList();
                    sqls.addAll(this.dialect.getCreateTableSql(this.upgradeCreateTable()));
                    sqls.add(String.format("INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')", 1, 0, "ddl版本号"));
                    sqls.add(String.format("INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')", 2, 0, "dml版本号"));
                    conn.setAutoCommit(false);
                    Iterator var6 = sqls.iterator();

                    while(var6.hasNext()) {
                        String sql = (String)var6.next();
                        stmt.addBatch(sql);
                    }

                    stmt.executeBatch();
                    conn.commit();
                } catch (Throwable var31) {
                    var4 = var31;
                    throw var31;
                } finally {
                    if (stmt != null) {
                        if (var4 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var30) {
                                var4.addSuppressed(var30);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Throwable var33) {
                var2 = var33;
                throw var33;
            } finally {
                if (conn != null) {
                    if (var2 != null) {
                        try {
                            conn.close();
                        } catch (Throwable var29) {
                            var2.addSuppressed(var29);
                        }
                    } else {
                        conn.close();
                    }
                }

            }
        } catch (Exception var35) {
            throw new FrameworkException(var35);
        }
    }

    private CreateTable upgradeCreateTable() {
        CreateTable table = new CreateTable();
        table.setTableName("sys_upgrade_version");
        table.setComment("系统更新升级版本记录表");
        List<Column> columns = new ArrayList();
        columns.add(this.buidIntColumn("id", "主键ID"));
        columns.add(this.buidIntColumn("version", "版本号"));
        columns.add(this.buidVarcharColumn("remark", "备注"));
        table.setColumns(columns);
        return table;
    }

    private Column buidIntColumn(String columnName, String comment) {
        return (Column) BeanBuilder.of(Column::new).set(Column::setColumnName, columnName).set(Column::setComment, comment).set(Column::setDataType, DataTypeEnum.INT).set(Column::setLength, 10).set(Column::setNullable, false).build();
    }

    private Column buidVarcharColumn(String columnName, String comment) {
        return (Column)BeanBuilder.of(Column::new).set(Column::setColumnName, columnName).set(Column::setComment, comment).set(Column::setDataType, DataTypeEnum.VARCHAR).set(Column::setLength, 50).set(Column::setNullable, false).build();
    }

    private void doUpgradeDdl(DatabaseUpgrade upgrade) {
        try {
            Connection conn = this.dataSource.getConnection();
            Throwable var3 = null;

            try {
                Statement stmt = conn.createStatement();
                Throwable var5 = null;

                try {
                    List<String> sqls = UpgradeDialectUtil.toDdlSql(this.dialect, upgrade);
                    conn.setAutoCommit(false);

                    String sql;
                    for(Iterator var7 = sqls.iterator(); var7.hasNext(); stmt.addBatch(sql)) {
                        sql = (String)var7.next();
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("DDL升级脚本：{}", sql);
                        }
                    }

                    stmt.addBatch(String.format("UPDATE sys_upgrade_version SET version=%d WHERE id=1", upgrade.getVersion()));
                    stmt.executeBatch();
                    conn.commit();
                } catch (Throwable var32) {
                    var5 = var32;
                    throw var32;
                } finally {
                    if (stmt != null) {
                        if (var5 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var31) {
                                var5.addSuppressed(var31);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Throwable var34) {
                var3 = var34;
                throw var34;
            } finally {
                if (conn != null) {
                    if (var3 != null) {
                        try {
                            conn.close();
                        } catch (Throwable var30) {
                            var3.addSuppressed(var30);
                        }
                    } else {
                        conn.close();
                    }
                }

            }

        } catch (Exception var36) {
            throw new FrameworkException(var36);
        }
    }

    private void doUpgradeSql(DatabaseUpgrade upgrade) {
        try {
            Connection conn = this.dataSource.getConnection();
            Throwable var3 = null;

            try {
                Statement stmt = conn.createStatement();
                Throwable var5 = null;

                try {
                    FunctionConverter converter = new DelegateFunctionConverter();
                    conn.setAutoCommit(false);

                    String sql;
                    for(Iterator var7 = upgrade.getSqls().iterator(); var7.hasNext(); stmt.addBatch(sql)) {
                        sql = (String)var7.next();
                        sql = converter.convert(sql, this.databaseId);
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("SQL升级脚本：{}", sql);
                        }
                    }

                    stmt.addBatch(String.format("UPDATE sys_upgrade_version SET version=%d WHERE id=2", upgrade.getVersion()));
                    stmt.executeBatch();
                    conn.commit();
                } catch (Throwable var32) {
                    var5 = var32;
                    throw var32;
                } finally {
                    if (stmt != null) {
                        if (var5 != null) {
                            try {
                                stmt.close();
                            } catch (Throwable var31) {
                                var5.addSuppressed(var31);
                            }
                        } else {
                            stmt.close();
                        }
                    }

                }
            } catch (Throwable var34) {
                var3 = var34;
                throw var34;
            } finally {
                if (conn != null) {
                    if (var3 != null) {
                        try {
                            conn.close();
                        } catch (Throwable var30) {
                            var3.addSuppressed(var30);
                        }
                    } else {
                        conn.close();
                    }
                }

            }

        } catch (Exception var36) {
            throw new FrameworkException(var36);
        }
    }
}
