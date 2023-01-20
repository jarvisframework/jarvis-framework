package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class H2UpgradeDialect extends PostgresqlUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new H2UpgradeDialect();

    public Collection<String> getModifyColumnSql(ModifyColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumns().iterator();

        while(var4.hasNext()) {
            Column column = (Column)var4.next();
            sqls.add(this.getModifyColumnSql(tableName, column));
            sqls.add(this.getModifyColumnDefault(tableName, column));
            sqls.add(this.getModifyColumnNullable(tableName, column));
            if (StringUtils.hasText(column.getComment())) {
                sqls.add(this.getColumnComment(tableName, column));
            }
        }

        return sqls;
    }

    private String getModifyColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName()).append(" ").append(this.getColumnDataType(column));
        return sb.toString();
    }

    private String getModifyColumnDefault(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName()).append(" SET DEFAULT ");
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" '").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(" NULL");
        }

        return sb.toString();
    }

    private String getModifyColumnNullable(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER ").append(column.getColumnName()).append(" SET ");
        if (column.isNullable()) {
            sb.append(" NULL");
        } else {
            sb.append(" NOT NULL");
        }

        return sb.toString();
    }

    public Collection<String> getRenameColumnSql(RenameColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        sqls.add(String.format("ALTER TABLE %s ALTER COLUMN %s RENAME TO %s", tableName, model.getColumnName(), model.getNewColumn().getColumnName()));
        if (StringUtils.hasText(model.getNewColumn().getComment())) {
            sqls.add(this.getColumnComment(tableName, model.getNewColumn()));
        }

        return sqls;
    }

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
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
