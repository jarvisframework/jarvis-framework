package com.jarvis.framework.database.upgrade.model;

public class Table implements DdlUpgrade {

    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
