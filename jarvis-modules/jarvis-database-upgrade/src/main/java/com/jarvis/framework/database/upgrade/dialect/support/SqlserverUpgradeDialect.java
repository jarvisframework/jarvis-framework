package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.constant.BaseColumnConstant;
import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.AddColumn;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.CreateTable;
import com.jarvis.framework.database.upgrade.model.DropColumn;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import com.jarvis.framework.database.upgrade.model.RenameTable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月2日
 */
public class SqlserverUpgradeDialect extends AbstractUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new SqlserverUpgradeDialect();

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getCreateTableSql(com.jarvis.framework.database.upgrade.model.CreateTable)
     */
    @Override
    public Collection<String> getCreateTableSql(CreateTable model) {
        final List<String> sqls = new ArrayList<>();
        final StringBuilder sb = new StringBuilder(128);

        sb.append("CREATE TABLE ").append(model.getTableName()).append("(");
        boolean isFirst = true;
        sqls.add(getAddTableComment(model.getTableName(), model.getComment()));

        for (final Column column : model.getColumns()) {
            if (!isFirst) {
                sb.append(",");
            }
            sb.append(column.getColumnName()).append(" ").append(getColumnDataType(column));
            if (BaseColumnConstant.ID.equalsIgnoreCase(column.getColumnName())) {
                if (column.isNullable()) {
                    sb.append(" NOT NULL");
                }
                sb.append(" PRIMARY KEY");
            }
            if (StringUtils.hasText(column.getDefaultValue())) {
                sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }
            sqls.add(getAddColumnComment(model.getTableName(), column));
            isFirst = false;
        }

        sb.append(")");

        sqls.add(0, sb.toString());

        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getRenameTableSql(
     *      com.jarvis.framework.database.upgrade.model.RenameTable)
     */
    @Override
    public Collection<String> getRenameTableSql(RenameTable model) {
        return Collections.singleton(String.format("EXEC sp_rename N'%s', N'%s'", model.getTableName(),
                model.getNewTableName()));
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getAddColumnSql(com.jarvis.framework.database.upgrade.model.AddColumn)
     */
    @Override
    public Collection<String> getAddColumnSql(AddColumn model) {
        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();
        for (final Column column : model.getColumns()) {
            sqls.add(getAddColumnSql(tableName, column));
            sqls.add(getAddColumnComment(tableName, column));
        }
        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect#getDropColumnSql(com.jarvis.framework.database.upgrade.model.DropColumn)
     */
    @Override
    public Collection<String> getDropColumnSql(DropColumn model) {

        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();

        for (final String columnName : model.getColumnNames()) {
            sqls.add(String.format("EXEC drop_column N'%s', N'%s'", tableName, columnName));
        }

        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getModifyColumnSql(com.jarvis.framework.database.upgrade.model.ModifyColumn)
     */
    @Override
    public Collection<String> getModifyColumnSql(ModifyColumn model) {
        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();

        for (final Column column : model.getColumns()) {
            sqls.add(getModifyColumnSql(tableName, column));
        }

        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getRenameColumnSql(com.jarvis.framework.database.upgrade.model.RenameColumn)
     */
    @Override
    public Collection<String> getRenameColumnSql(RenameColumn model) {
        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();

        sqls.add(String.format("EXEC sp_rename N'%s.[%s]', N'%s', 'column'", tableName,
                model.getColumnName(), model.getNewColumn().getColumnName()));
        sqls.add(String.format("EXEC comment_column N'%s', N'%s', '%s'", tableName,
                model.getNewColumn().getColumnName(), model.getNewColumn().getComment()));
        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect#getColumnDataType(com.jarvis.framework.database.upgrade.model.Column)
     */
    @Override
    protected String getColumnDataType(Column model) {
        final StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append("NVARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType()) {
            sb.append("NTEXT");
        } else if (DataTypeEnum.DATE == model.getDataType() || DataTypeEnum.DATETIME == model.getDataType()) {
            sb.append(model.getDataType().toString().toUpperCase());
        } else if (DataTypeEnum.INT == model.getDataType()) {
            sb.append("INT");
        } else if (DataTypeEnum.BIGINT == model.getDataType()) {
            sb.append("BIGINT");
        } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
            sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
        }
        if (!model.isNullable()) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    private String getAddTableComment(String tableName, String comment) {
        final StringBuffer sql = new StringBuffer();
        sql.append("EXEC sp_addextendedproperty N'MS_Description', N'").append(comment)
                .append("', N'user', N'dbo', N'table', N'").append(tableName).append("', null, null ");
        return sql.toString();
    }

    private String getAddColumnComment(String tableName, Column column) {
        final StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_addextendedproperty N'MS_Description', N'").append(column.getComment())
                .append("', N'user', N'dbo', N'table', N'").append(tableName).append("', N'column', N'")
                .append(column.getColumnName()).append("' ");
        return sql.toString();
    }

    private String getAddColumnSql(String tableName, Column column) {
        final StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ADD ").append(column.getColumnName()).append(" ")
                .append(getColumnDataType(column));
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }
        return sb.toString();
    }

    private String getModifyColumnSql(String tableName, Column column) {
        final StringBuilder sb = new StringBuilder();
        sb.append("EXEC modify_column N'").append(tableName).append("'")
                .append(", N'").append(column.getColumnName()).append("'")
                .append(", N'").append(getColumnDataType(column)).append("'");

        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(", N'").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(", null");
        }

        if (StringUtils.hasText(column.getComment())) {
            sb.append(", N'").append(column.getComment()).append("'");
        } else {
            sb.append(", null");
        }
        return sb.toString();
    }

}
