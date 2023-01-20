package com.jarvis.framework.database.upgrade.dialect.support;

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
import java.util.Iterator;
import java.util.List;

public class KingbaseUpgradeDialect extends AbstractUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new KingbaseUpgradeDialect();

    public Collection<String> getCreateTableSql(CreateTable model) {
        List<String> sqls = new ArrayList();
        StringBuilder sb = new StringBuilder(128);
        sb.append("CREATE TABLE ").append(model.getTableName()).append("(");
        boolean isFirst = true;
        sqls.add(this.getTableComment(model.getTableName(), model.getComment()));

        for(Iterator var5 = model.getColumns().iterator(); var5.hasNext(); isFirst = false) {
            Column column = (Column)var5.next();
            if (!isFirst) {
                sb.append(",");
            }

            sb.append(column.getColumnName()).append(this.getColumnDataType(column));
            if (!column.isNullable() || "id".equalsIgnoreCase(column.getColumnName())) {
                sb.append(" NOT NULL");
            }

            if (StringUtils.hasText(column.getDefaultValue())) {
                sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }

            if ("id".equalsIgnoreCase(column.getColumnName())) {
                sb.append(" PRIMARY KEY");
            }

            sqls.add(this.getColumnComment(model.getTableName(), column));
        }

        sb.append(")");
        sqls.add(0, sb.toString());
        return sqls;
    }

    public Collection<String> getAddColumnSql(AddColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumns().iterator();

        while(var4.hasNext()) {
            Column column = (Column)var4.next();
            sqls.add(this.getAddColumnSql(tableName, column));
            sqls.add(this.getColumnComment(tableName, column));
        }

        return sqls;
    }

    public Collection<String> getModifyColumnSql(ModifyColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumns().iterator();

        while(var4.hasNext()) {
            Column column = (Column)var4.next();
            sqls.add(this.getModifyColumnSql(tableName, column));
            if (StringUtils.hasText(column.getComment())) {
                sqls.add(this.getColumnComment(tableName, column));
            }
        }

        return sqls;
    }

    public Collection<String> getRenameColumnSql(RenameColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        sqls.add(String.format("ALTER TABLE %s RENAME COLUMN %s TO %s", tableName, model.getColumnName(), model.getNewColumn().getColumnName()));
        if (StringUtils.hasText(model.getNewColumn().getComment())) {
            sqls.add(this.getColumnComment(tableName, model.getNewColumn()));
        }

        return sqls;
    }

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT != model.getDataType() && DataTypeEnum.DATE != model.getDataType()) {
            if (DataTypeEnum.DATETIME == model.getDataType()) {
                sb.append(" TIMESTAMP ");
            } else if (DataTypeEnum.INT == model.getDataType()) {
                sb.append(" INTEGER ");
            } else if (DataTypeEnum.BIGINT == model.getDataType()) {
                sb.append(" BIGINT ");
            } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
                sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
            }
        } else {
            sb.append(" ").append(model.getDataType().toString().toUpperCase());
        }

        return sb.toString();
    }

    private String getTableComment(String tableName, String comment) {
        return "COMMENT ON TABLE " + tableName + " IS '" + comment + "'";
    }

    private String getColumnComment(String tableName, Column column) {
        return "COMMENT ON COLUMN " + tableName + "." + column.getColumnName() + " IS '" + column.getComment() + "'";
    }

    private String getAddColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ADD COLUMN ").append(column.getColumnName()).append(this.getColumnDataType(column));
        if (!column.isNullable()) {
            sb.append(" NOT NULL");
        }

        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }

        return sb.toString();
    }

    private String getModifyColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER COLUMN ").append(column.getColumnName()).append(" TYPE ").append(this.getColumnDataType(column));
        sb.append(", ALTER ").append(column.getColumnName());
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(" DEFAULT NULL");
        }

        return sb.toString();
    }
}
