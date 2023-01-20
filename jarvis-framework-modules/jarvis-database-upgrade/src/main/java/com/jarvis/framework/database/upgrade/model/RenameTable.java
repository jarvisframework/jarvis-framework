package com.jarvis.framework.database.upgrade.model;

public class RenameTable implements DdlUpgrade {

    private String tableName;

    private String newTableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }
}
