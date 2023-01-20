package com.jarvis.framework.database.upgrade.dialect.support;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;
import com.jarvis.framework.database.upgrade.dialect.AbstractUpgradeDialect;
import com.jarvis.framework.database.upgrade.dialect.UpgradeDialect;
import com.jarvis.framework.database.upgrade.model.AddColumn;
import com.jarvis.framework.database.upgrade.model.Column;
import com.jarvis.framework.database.upgrade.model.CreateTable;
import com.jarvis.framework.database.upgrade.model.DropColumn;
import com.jarvis.framework.database.upgrade.model.DropIndex;
import com.jarvis.framework.database.upgrade.model.ModifyColumn;
import com.jarvis.framework.database.upgrade.model.RenameColumn;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OracleUpgradeDialect extends AbstractUpgradeDialect {
    public static final UpgradeDialect INSTANCE = new OracleUpgradeDialect();

    public OracleUpgradeDialect() {
    }

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
            if ("id".equalsIgnoreCase(column.getColumnName())) {
                sb.append(" NOT NULL PRIMARY KEY");
            } else {
                if (StringUtils.hasText(column.getDefaultValue())) {
                    sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
                }

                if (!column.isNullable()) {
                    sb.append(" NOT NULL");
                }
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
            if (!column.isNullable()) {
                sqls.add(String.format("ALTER TABLE %s MODIFY %s NOT NULL", tableName, column.getColumnName()));
            }
        }

        return sqls;
    }

    private String getAddColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" ADD ").append(column.getColumnName()).append(this.getColumnDataType(column));
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        }

        if (!column.isNullable()) {
            sb.append(" NOT NULL ");
        }

        return sb.toString();
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

    private String getModifyColumnSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("ALTER TABLE ").append(tableName).append(" MODIFY ").append(column.getColumnName()).append(this.getColumnDataType(column));
        if (StringUtils.hasText(column.getDefaultValue())) {
            sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(" DEFAULT NULL");
        }

        if (!column.isNullable()) {
            sb.append(" NOT NULL ");
        }

        return sb.toString();
    }

    public Collection<String> getRenameColumnSql(RenameColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        sqls.add("ALTER TABLE " + tableName + " RENAME COLUMN " + model.getColumnName() + " TO " + model.getNewColumn().getColumnName());
        if (StringUtils.hasText(model.getNewColumn().getComment())) {
            sqls.add(this.getColumnComment(tableName, model.getNewColumn()));
        }

        return sqls;
    }

    public Collection<String> getDropColumnSql(DropColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumnNames().iterator();

        while(var4.hasNext()) {
            String columnName = (String)var4.next();
            sqls.add("ALTER TABLE " + tableName + " DROP COLUMN " + columnName);
        }

        return sqls;
    }

    public Collection<String> getDropIndexSql(DropIndex model) {
        return Collections.singletonList("DROP INDEX " + model.getIndexName());
    }

    protected String getTableComment(String tableName, String comment) {
        return "COMMENT ON TABLE " + tableName + " IS '" + comment + "'";
    }

    protected String getColumnComment(String tableName, Column column) {
        return "COMMENT ON COLUMN " + tableName + "." + column.getColumnName() + " IS '" + column.getComment() + "'";
    }

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR2(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT == model.getDataType()) {
            sb.append(" CLOB ");
        } else if (DataTypeEnum.DATE != model.getDataType() && DataTypeEnum.DATETIME != model.getDataType()) {
            if (DataTypeEnum.INT != model.getDataType() && DataTypeEnum.BIGINT != model.getDataType()) {
                if (DataTypeEnum.DECIMAL == model.getDataType()) {
                    sb.append(" NUMBER(").append(model.getLength()).append(",").append(model.getScale()).append(")");
                }
            } else {
                sb.append(" NUMBER(").append(model.getLength()).append(")");
            }
        } else {
            sb.append(" DATE");
        }

        return sb.toString();
    }
}
