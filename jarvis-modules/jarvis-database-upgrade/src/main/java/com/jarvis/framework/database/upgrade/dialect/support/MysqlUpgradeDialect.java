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
import java.util.Collections;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月2日
 */
public class MysqlUpgradeDialect extends AbstractUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new MysqlUpgradeDialect();

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

        for (final Column column : model.getColumns()) {
            if (!isFirst) {
                sb.append(",");
            }
            sb.append(getColumnDataType(column));

            if (BaseColumnConstant.ID.equalsIgnoreCase(column.getColumnName())) {
                if (column.isNullable()) {
                    sb.append(" NOT NULL");
                }
                sb.append(" PRIMARY KEY");
            } else {
                if (StringUtils.hasText(column.getDefaultValue())) {
                    sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
                }
            }

            sb.append(" COMMENT '").append(column.getComment()).append("'");

            isFirst = false;
        }

        sb.append(") COMMENT = '").append(model.getComment()).append("'");

        sqls.add(sb.toString());

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
            if (StringUtils.hasText(column.getDefaultValue())) {
                sqls.add(getSetDefaultSql(tableName, column));
            }
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
            sqls.add(getSetDefaultSql(tableName, column));
        }
        return sqls;
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.UpgradeDialect#getRenameColumnSql(
     *      com.jarvis.framework.database.upgrade.model.RenameColumn)
     */
    @Override
    public Collection<String> getRenameColumnSql(RenameColumn model) {
        return Collections.singletonList(
                getRenameColumnSql(model.getTableName(), model.getColumnName(), model.getNewColumn()));
    }

    /**
     *
     * @see com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect#getColumnDataType(com.jarvis.framework.database.upgrade.model.Column)
     */
    @Override
    protected String getColumnDataType(Column model) {
        final StringBuilder sb = new StringBuilder(64);
        sb.append(model.getColumnName());
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType() || DataTypeEnum.DATE == model.getDataType()
                || DataTypeEnum.DATETIME == model.getDataType()) {
            sb.append(" ").append(model.getDataType().toString().toUpperCase());
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

    private String getAddColumnSql(String tableName, Column column) {
        final String formatter = "ALTER TABLE %s ADD %s COMMENT '%s'";
        return String.format(formatter, tableName, getColumnDataType(column), column.getComment());
    }

    private String getModifyColumnSql(String tableName, Column column) {
        final String formatter = "ALTER TABLE %s MODIFY %s COMMENT '%s'";
        return String.format(formatter, tableName, getColumnDataType(column), column.getComment());
    }

    private String getRenameColumnSql(String tableName, String orgColumnName, Column column) {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("ALTER TABLE ").append(tableName).append(" CHANGE ").append(orgColumnName).append(" ")
                .append(getColumnDataType(column)).append(" DEFAULT ");
        if (!StringUtils.hasText(column.getDefaultValue())) {
            sb.append("NULL");
        } else {
            if (DataTypeEnum.INT == column.getDataType() || DataTypeEnum.BIGINT == column.getDataType()) {
                sb.append(column.getDefaultValue());
            } else {
                sb.append("'").append(column.getDefaultValue()).append("'");
            }
        }
        sb.append(" COMMENT '").append(column.getComment()).append("'");
        return sb.toString();
    }

    private String getSetDefaultSql(String tableName, Column column) {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER COLUMN ").append(column.getColumnName())
                .append(" SET DEFAULT ");
        if (!StringUtils.hasText(column.getDefaultValue())) {
            return sb.append("NULL").toString();
        }
        if (DataTypeEnum.INT == column.getDataType() || DataTypeEnum.BIGINT == column.getDataType()) {
            sb.append(column.getDefaultValue());
        } else {
            sb.append("'").append(column.getDefaultValue()).append("'");

        }
        return sb.toString();
    }

}
