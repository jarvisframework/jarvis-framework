package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.constant.BaseColumnConstant;
import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.AddColumn;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.CreateTable;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月8日
 */
public class PostgresqlUpgradeDialect extends AbstractUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new PostgresqlUpgradeDialect();

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
        sqls.add(getTableComment(model.getTableName(), model.getComment()));

        for (final Column column : model.getColumns()) {
            if (!isFirst) {
                sb.append(",");
            }
            sb.append(column.getColumnName()).append(getColumnDataType(column));

            if (BaseColumnConstant.ID.equalsIgnoreCase(column.getColumnName())) {
                if (column.isNullable()) {
                    sb.append(" NOT NULL");
                }
                sb.append(" PRIMARY KEY");
            }
            if (StringUtils.hasText(column.getDefaultValue())) {
                sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }

            sqls.add(getColumnComment(model.getTableName(), column));
            isFirst = false;
        }

        sb.append(")");

        sqls.add(0, sb.toString());

        return sqls;
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
            sqls.add(getColumnComment(tableName, column));
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
            if (StringUtils.hasText(column.getComment())) {
                sqls.add(getColumnComment(tableName, column));
            }
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
        sqls.add(String.format("ALTER TABLE %s RENAME COLUMN %s TO %s", tableName, model.getColumnName(),
                model.getNewColumn().getColumnName()));
        if (StringUtils.hasText(model.getNewColumn().getComment())) {
            sqls.add(getColumnComment(tableName, model.getNewColumn()));
        }
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
            sb.append(" VARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType() || DataTypeEnum.DATE == model.getDataType()) {
            sb.append(" ").append(model.getDataType().toString().toUpperCase());
        } else if (DataTypeEnum.DATETIME == model.getDataType()) {
            sb.append(" TIMESTAMP ");
        } else if (DataTypeEnum.INT == model.getDataType()) {
            sb.append(" INT(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.BIGINT == model.getDataType()) {
            sb.append(" BIGINT(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
            sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
        }
        if (!model.isNullable()) {
            sb.append(" NOT NULL");
        }
        return sb.toString();
    }

    private String getTableComment(String tableName, String comment) {
        return "COMMENT ON TABLE " + tableName + " IS '" + comment + "'";
    }

    protected String getColumnComment(String tableName, Column column) {
        return "COMMENT ON COLUMN " + tableName + "." + column.getColumnName() + " IS '" + column.getComment() + "'";
    }

    private String getAddColumnSql(String tableName, Column column) {
        final StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ADD ").append(column.getColumnName())
                .append(getColumnDataType(column));

        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }
        return sb.toString();
    }

    private String getModifyColumnSql(String tableName, Column column) {

        final StringBuilder sb = new StringBuilder();
        // 修改字段
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName()).append(" TYPE ")
                .append(getColumnDataType(column));
        sb.append(", ALTER ").append(column.getColumnName());
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(" DEFAULT null");
        }
        return sb.toString();
    }

}
