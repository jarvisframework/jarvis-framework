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
import java.util.List;
import java.util.Optional;

/**
 * 执行数据库脚本更新处理器
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
public class UpgradeProcessorApplicationRunner implements ApplicationRunner, Ordered {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    private final String databaseId;

    private final UpgradeProperties properties;

    private final UpgradeDialect dialect;

    private final UpgradeVersion version;

    private final String selectVersionSql = "SELECT id, version FROM sys_upgrade_version WHERE id between 1 and 2";

    private final String updateDdlVersionSql = "UPDATE sys_upgrade_version SET version=%d WHERE id=1";

    private final String updateDmlVersionSql = "UPDATE sys_upgrade_version SET version=%d WHERE id=2";

    private final String initVersionSql = "INSERT INTO sys_upgrade_version (id, version, remark) VALUES (%d, %d, '%s')";

    /**
     * @param dataSource
     * @param properties
     * @throws SQLException
     */
    public UpgradeProcessorApplicationRunner(DataSource dataSource, UpgradeProperties properties) throws SQLException {
        super();
        this.dataSource = dataSource;
        this.databaseId = DatabaseProductNameUtil.getDatabaseProductName(dataSource);
        this.properties = properties;
        dialect = UpgradeDialectFactory.getDialect(databaseId);
        //delegateDialect = new DelegateUpgradeDialect(dialect);
        version = getVersion();
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    /**
     *
     * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (log.isInfoEnabled()) {
            log.info("开始执行数据库脚本升级......");
        }

        Optional.ofNullable(properties).map(properties -> properties.getDatabaseUpgrades())
                .ifPresent(upgrades -> {
                    upgrades.forEach(upgrade -> {
                        final int version = upgrade.getVersion();
                        if (version > this.version.getDdlVersion()) {
                            doUpgradeDdl(upgrade);
                        }
                        if (version > this.version.getDmlVersion()) {
                            doUpgradeSql(upgrade);
                        }
                    });
                });

        if (log.isInfoEnabled()) {
            log.info("完成执行数据库脚本升级......");
        }

    }

    private UpgradeVersion getVersion() {
        final UpgradeVersion version = new UpgradeVersion();

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectVersionSql);) {
            while (rs.next()) {
                if (1 == rs.getInt(1)) {
                    version.setDdlVersion(rs.getInt(2));
                } else {
                    version.setDmlVersion(rs.getInt(2));
                }
            }
            initVersion(conn, version);
        } catch (final Exception e) {
            if (e instanceof FrameworkException) {
                throw (FrameworkException) e;
            } else {
                createVersionTable();
            }
        }
        if (log.isInfoEnabled()) {
            log.info("当前版本ddl_version={}, dml_version={}", version.getDdlVersion(), version.getDmlVersion());
        }
        return version;
    }

    private void initVersion(Connection conn, UpgradeVersion version) {
        final boolean isInitDdl = -1 == version.getDdlVersion();
        final boolean isInitDml = -1 == version.getDmlVersion();
        if (!isInitDdl && !isInitDml) {
            return;
        }
        try (Statement stmt = conn.createStatement();) {
            conn.setAutoCommit(false);
            if (isInitDdl) {
                stmt.addBatch(String.format(initVersionSql, 1, 0, "ddl版本号"));
            }
            if (isInitDml) {
                stmt.addBatch(String.format(initVersionSql, 2, 0, "dml版本号"));
            }
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            throw new FrameworkException("初始化数据库更新脚本的版本号出错", e);
        }
        if (isInitDdl) {
            if (log.isErrorEnabled()) {
                log.error("ddl版本号在表[sys_upgrade_version]中不存在，程序已恢复最初版本号；如有问题，请手动修正！");
            }
            version.setDdlVersion(0);
        }
        if (isInitDml) {
            if (log.isErrorEnabled()) {
                log.error("dml版本号在表[sys_upgrade_version]中不存在，程序已恢复最初版本号；如有问题，请手动修正！");
            }
            version.setDmlVersion(0);
        }
    }

    private void createVersionTable() {
        if (log.isInfoEnabled()) {
            log.info("初始化sys_upgrade_version表结构");
        }
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {

            final List<String> sqls = new ArrayList<>();

            sqls.addAll(dialect.getCreateTableSql(upgradeCreateTable()));
            sqls.add(String.format(initVersionSql, 1, 0, "ddl版本号"));
            sqls.add(String.format(initVersionSql, 2, 0, "dml版本号"));

            conn.setAutoCommit(false);
            for (final String sql : sqls) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    private CreateTable upgradeCreateTable() {
        final CreateTable table = new CreateTable();
        table.setTableName("sys_upgrade_version");
        table.setComment("系统更新升级版本记录表");
        final List<Column> columns = new ArrayList<>();
        columns.add(buidIntColumn("id", "主键ID"));
        columns.add(buidIntColumn("version", "版本号"));
        columns.add(buidVarcharColumn("remark", "备注"));
        table.setColumns(columns);
        return table;
    }

    private Column buidIntColumn(String columnName, String comment) {
        return BeanBuilder.<Column>of(Column::new).set(Column::setColumnName, columnName)
                .set(Column::setComment, comment)
                .set(Column::setDataType, DataTypeEnum.INT)
                .set(Column::setLength, 10)
                .set(Column::setNullable, false).build();
    }

    private Column buidVarcharColumn(String columnName, String comment) {
        return BeanBuilder.<Column>of(Column::new).set(Column::setColumnName, columnName)
                .set(Column::setComment, comment)
                .set(Column::setDataType, DataTypeEnum.VARCHAR)
                .set(Column::setLength, 50)
                .set(Column::setNullable, false).build();
    }

    private void doUpgradeDdl(DatabaseUpgrade upgrade) {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {

            final List<String> sqls = UpgradeDialectUtil.toDdlSql(dialect, upgrade);
            conn.setAutoCommit(false);
            for (final String sql : sqls) {
                if (log.isDebugEnabled()) {
                    log.debug("DDL升级脚本：{}", sql);
                }
                stmt.addBatch(sql);
            }
            stmt.addBatch(String.format(updateDdlVersionSql, upgrade.getVersion()));
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    private void doUpgradeSql(DatabaseUpgrade upgrade) {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {
            final FunctionConverter converter = new DelegateFunctionConverter();
            conn.setAutoCommit(false);
            for (String sql : upgrade.getSqls()) {
                sql = converter.convert(sql, databaseId);
                if (log.isDebugEnabled()) {
                    log.debug("SQL升级脚本：{}", sql);
                }
                stmt.addBatch(sql);
            }
            stmt.addBatch(String.format(updateDmlVersionSql, upgrade.getVersion()));
            stmt.executeBatch();
            conn.commit();
        } catch (final Exception e) {
            throw new FrameworkException(e);
        }
    }

    /*public static void main(String[] args) {
        final UpgradeProperties properties = new UpgradeProperties();
        Optional.ofNullable(properties).map(p -> p.getDatabaseUpgrades()).ifPresent(upgrades -> {
            upgrades.forEach(upgrade -> {
                System.out.println(upgrade);
            });
        });
    }*/

}
