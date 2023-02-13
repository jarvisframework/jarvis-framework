package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月7日
 */
public class H2UpgradeDialect extends PostgresqlUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new H2UpgradeDialect();

    @Override
    public Collection<String> getModifyColumnSql(ModifyColumn model) {
        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();
        for (final Column column : model.getColumns()) {
            sqls.add(getModifyColumnSql(tableName, column));
            sqls.add(getModifyColumnDefault(tableName, column));
            sqls.add(getModifyColumnNullable(tableName, column));
            if (StringUtils.hasText(column.getComment())) {
                sqls.add(getColumnComment(tableName, column));
            }
        }
        return sqls;
    }

    private String getModifyColumnSql(String tableName, Column column) {

        final StringBuilder sb = new StringBuilder();
        // 修改字段
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName()).append(" ")
                .append(getColumnDataType(column));
        return sb.toString();
    }

    private String getModifyColumnDefault(String tableName, Column column) {

        final StringBuilder sb = new StringBuilder();
        // 修改字段
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName())
                .append(" SET DEFAULT ");
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" '").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(" NULL");
        }
        return sb.toString();
    }

    private String getModifyColumnNullable(String tableName, Column column) {

        final StringBuilder sb = new StringBuilder();
        // 修改字段
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName())
                .append(" SET ");
        if (column.isNullable()) {
            sb.append(" NULL");
        } else {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    /**
     * @see com.jarvis.framework.database.upgrade.dialect.support.PostgresqlUpgradeDialect#getRenameColumnSql(com.jarvis.framework.database.upgrade.model.RenameColumn)
     */
    @Override
    public Collection<String> getRenameColumnSql(RenameColumn model) {
        final List<String> sqls = new ArrayList<>();
        final String tableName = model.getTableName();
        sqls.add(String.format("ALTER TABLE %s ALTER COLUMN %s RENAME TO %s", tableName, model.getColumnName(),
                model.getNewColumn().getColumnName()));
        if (StringUtils.hasText(model.getNewColumn().getComment())) {
            sqls.add(getColumnComment(tableName, model.getNewColumn()));
        }
        return sqls;
    }

    /**
     * @see com.jarvis.framework.database.upgrade.dialect.support.PostgresqlUpgradeDialect#getColumnDataType(com.jarvis.framework.database.upgrade.model.Column)
     */
    @Override
    protected String getColumnDataType(Column model) {
        final StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType()) {
            sb.append(" CLOB ");
        } else if (DataTypeEnum.DATE == model.getDataType()) {
            sb.append(" ").append(model.getDataType().toString().toUpperCase());
        } else if (DataTypeEnum.DATETIME == model.getDataType()) {
            sb.append(" TIMESTAMP ");
        } else if (DataTypeEnum.INT == model.getDataType()) {
            sb.append(" INT ");
        } else if (DataTypeEnum.BIGINT == model.getDataType()) {
            sb.append(" BIGINT ");
        } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
            sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
        }
        if (!model.isNullable()) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }
}
