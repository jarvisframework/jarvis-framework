package com.jarvis.framework.database.upgrade.dialect.support;

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
import java.util.Iterator;
import java.util.List;

public class SqlserverUpgradeDialect extends AbstractUpgradeDialect {

    public static final UpgradeDialect INSTANCE = new SqlserverUpgradeDialect();

    public Collection<String> getCreateTableSql(CreateTable model) {
        List<String> sqls = new ArrayList();
        StringBuilder sb = new StringBuilder(128);
        sb.append("CREATE TABLE ").append(model.getTableName()).append("(");
        boolean isFirst = true;
        sqls.add(this.getAddTableComment(model.getTableName(), model.getComment()));

        for(Iterator var5 = model.getColumns().iterator(); var5.hasNext(); isFirst = false) {
            Column column = (Column)var5.next();
            if (!isFirst) {
                sb.append(",");
            }

            sb.append(column.getColumnName()).append(" ").append(this.getColumnDataType(column));
            if ("id".equalsIgnoreCase(column.getColumnName())) {
                if (column.isNullable()) {
                    sb.append(" NOT NULL");
                }

                sb.append(" PRIMARY KEY");
            }

            if (StringUtils.hasText(column.getDefaultValue())) {
                sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }

            sqls.add(this.getAddColumnComment(model.getTableName(), column));
        }

        sb.append(")");
        sqls.add(0, sb.toString());
        return sqls;
    }

    public Collection<String> getRenameTableSql(RenameTable model) {
        return Collections.singleton(String.format("EXEC sp_rename N'%s', N'%s'", model.getTableName(), model.getNewTableName()));
    }

    public Collection<String> getAddColumnSql(AddColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumns().iterator();

        while(var4.hasNext()) {
            Column column = (Column)var4.next();
            sqls.add(this.getAddColumnSql(tableName, column));
            sqls.add(this.getAddColumnComment(tableName, column));
        }

        return sqls;
    }

    public Collection<String> getDropColumnSql(DropColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumnNames().iterator();

        while(var4.hasNext()) {
            String columnName = (String)var4.next();
            sqls.add(String.format("EXEC drop_column N'%s', N'%s'", tableName, columnName));
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
        }

        return sqls;
    }

    public Collection<String> getRenameColumnSql(RenameColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        sqls.add(String.format("EXEC sp_rename N'%s.[%s]', N'%s', 'column'", tableName, model.getColumnName(), model.getNewColumn().getColumnName()));
        sqls.add(String.format("EXEC comment_column N'%s', N'%s', '%s'", tableName, model.getNewColumn().getColumnName(), model.getNewColumn().getComment()));
        return sqls;
    }

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append("NVARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType()) {
            sb.append("NTEXT");
        } else if (DataTypeEnum.DATE != model.getDataType() && DataTypeEnum.DATETIME != model.getDataType()) {
            if (DataTypeEnum.INT == model.getDataType()) {
                sb.append("INT");
            } else if (DataTypeEnum.BIGINT == model.getDataType()) {
                sb.append("BIGINT");
            } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
                sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
            }
        } else {
            sb.append(model.getDataType().toString().toUpperCase());
        }

        if (!model.isNullable()) {
            sb.append(" NOT NULL");
        }

        return sb.toString();
    }

    private String getAddTableComment(String tableName, String comment) {
        StringBuffer sql = new StringBuffer();
        sql.append("EXEC sp_addextendedproperty N'MS_Description', N'").append(comment).append("', N'user', N'dbo', N'table', N'").append(tableName).append("', null, null ");
        return sql.toString();
    }

    private String getAddColumnComment(String tableName, Column column) {
        StringBuilder sql = new StringBuilder();
        sql.append("EXEC sp_addextendedproperty N'MS_Description', N'").append(column.getComment()).append("', N'user', N'dbo', N'table', N'").append(tableName).append("', N'column', N'").append(column.getColumnName()).append("' ");
        return sql.toString();
    }

    private String getAddColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ADD ").append(column.getColumnName()).append(" ").append(this.getColumnDataType(column));
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }

        return sb.toString();
    }

    private String getModifyColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("EXEC modify_column N'").append(tableName).append("'").append(", N'").append(column.getColumnName()).append("'").append(", N'").append(this.getColumnDataType(column)).append("'");
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
