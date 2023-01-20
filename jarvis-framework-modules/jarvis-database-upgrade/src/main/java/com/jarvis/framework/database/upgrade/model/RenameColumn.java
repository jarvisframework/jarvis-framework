package com.jarvis.framework.database.upgrade.model;

public class RenameColumn implements DdlUpgrade {

    private String tableName;

    private String columnName;
    
    private Column newColumn;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Column getNewColumn() {
        return this.newColumn;
    }

    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }
}
