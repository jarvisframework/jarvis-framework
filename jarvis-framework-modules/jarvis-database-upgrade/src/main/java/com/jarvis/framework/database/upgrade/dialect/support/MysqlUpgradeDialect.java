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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MysqlUpgradeDialect extends AbstractUpgradeDialect {
    public static final UpgradeDialect INSTANCE = new MysqlUpgradeDialect();

    public MysqlUpgradeDialect() {
    }

    public Collection<String> getCreateTableSql(CreateTable model) {
        List<String> sqls = new ArrayList();
        StringBuilder sb = new StringBuilder(128);
        sb.append("CREATE TABLE ").append(model.getTableName()).append("(");
        boolean isFirst = true;

        for(Iterator var5 = model.getColumns().iterator(); var5.hasNext(); isFirst = false) {
            Column column = (Column)var5.next();
            if (!isFirst) {
                sb.append(",");
            }

            sb.append(this.getColumnDataType(column));
            if ("id".equalsIgnoreCase(column.getColumnName())) {
                if (column.isNullable()) {
                    sb.append(" NOT NULL");
                }

                sb.append(" PRIMARY KEY");
            } else if (StringUtils.hasText(column.getDefaultValue())) {
                sb.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }

            sb.append(" COMMENT '").append(column.getComment()).append("'");
        }

        sb.append(") COMMENT = '").append(model.getComment()).append("'");
        sqls.add(sb.toString());
        return sqls;
    }

    public Collection<String> getAddColumnSql(AddColumn model) {
        List<String> sqls = new ArrayList();
        String tableName = model.getTableName();
        Iterator var4 = model.getColumns().iterator();

        while(var4.hasNext()) {
            Column column = (Column)var4.next();
            sqls.add(this.getAddColumnSql(tableName, column));
            if (StringUtils.hasText(column.getDefaultValue())) {
                sqls.add(this.getSetDefaultSql(tableName, column));
            }
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
            sqls.add(this.getSetDefaultSql(tableName, column));
        }

        return sqls;
    }

    public Collection<String> getRenameColumnSql(RenameColumn model) {
        return Collections.singletonList(this.getRenameColumnSql(model.getTableName(), model.getColumnName(), model.getNewColumn()));
    }

    protected String getColumnDataType(Column model) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(model.getColumnName());
        if (DataTypeEnum.VARCHAR == model.getDataType()) {
            sb.append(" VARCHAR(").append(model.getLength()).append(")");
        } else if (DataTypeEnum.TEXT != model.getDataType() && DataTypeEnum.DATE != model.getDataType() && DataTypeEnum.DATETIME != model.getDataType()) {
            if (DataTypeEnum.INT == model.getDataType()) {
                sb.append(" INT(").append(model.getLength()).append(")");
            } else if (DataTypeEnum.BIGINT == model.getDataType()) {
                sb.append(" BIGINT(").append(model.getLength()).append(")");
            } else if (DataTypeEnum.DECIMAL == model.getDataType()) {
                sb.append(" DECIMAL(").append(model.getLength()).append(",").append(model.getScale()).append(")");
            }
        } else {
            sb.append(" ").append(model.getDataType().toString().toUpperCase());
        }

        if (!model.isNullable()) {
            sb.append(" NOT NULL");
        }

        return sb.toString();
    }

    private String getAddColumnSql(String tableName, Column column) {
        String formatter = "ALTER TABLE %s ADD %s COMMENT '%s'";
        return String.format("ALTER TABLE %s ADD %s COMMENT '%s'", tableName, this.getColumnDataType(column), column.getComment());
    }

    private String getModifyColumnSql(String tableName, Column column) {
        String formatter = "ALTER TABLE %s MODIFY %s COMMENT '%s'";
        return String.format("ALTER TABLE %s MODIFY %s COMMENT '%s'", tableName, this.getColumnDataType(column), column.getComment());
    }

    private String getRenameColumnSql(String tableName, String orgColumnName, Column column) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("ALTER TABLE ").append(tableName).append(" CHANGE ").append(orgColumnName).append(" ").append(this.getColumnDataType(column)).append(" DEFAULT ");
        if (!StringUtils.hasText(column.getDefaultValue())) {
            sb.append("NULL");
        } else if (DataTypeEnum.INT != column.getDataType() && DataTypeEnum.BIGINT != column.getDataType()) {
            sb.append("'").append(column.getDefaultValue()).append("'");
        } else {
            sb.append(column.getDefaultValue());
        }

        sb.append(" COMMENT '").append(column.getComment()).append("'");
        return sb.toString();
    }

    private String getSetDefaultSql(String tableName, Column column) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("ALTER TABLE ").append(tableName).append(" ALTER COLUMN ").append(column.getColumnName()).append(" SET DEFAULT ");
        if (!StringUtils.hasText(column.getDefaultValue())) {
            return sb.append("NULL").toString();
        } else {
            if (DataTypeEnum.INT != column.getDataType() && DataTypeEnum.BIGINT != column.getDataType()) {
                sb.append("'").append(column.getDefaultValue()).append("'");
            } else {
                sb.append(column.getDefaultValue());
            }

            return sb.toString();
        }
    }
}
