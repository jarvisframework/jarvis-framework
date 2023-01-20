package com.jarvis.framework.database.upgrade.model;

import java.util.List;

public class DropColumn implements DdlUpgrade {

    private String tableName;

    private List<String> columnNames;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
