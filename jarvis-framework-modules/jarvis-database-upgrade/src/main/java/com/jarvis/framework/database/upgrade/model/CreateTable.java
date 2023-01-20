package com.jarvis.framework.database.upgrade.model;

import java.util.List;

public class CreateTable implements DdlUpgrade {
    private String tableName;
    private String comment;
    private List<Column> columns;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
