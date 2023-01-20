package com.jarvis.framework.database.upgrade.model;

public class DropIndex implements DdlUpgrade {
    private String tableName;
    private String indexName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
