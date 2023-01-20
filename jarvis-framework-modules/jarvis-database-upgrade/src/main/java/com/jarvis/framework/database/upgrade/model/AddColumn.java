package com.jarvis.framework.database.upgrade.model;

import java.util.List;

public class AddColumn implements DdlUpgrade {
    private String tableName;
    private List<Column> columns;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
