package com.jarvis.framework.database.upgrade.model;

import com.jarvis.framework.database.upgrade.constant.IndexTypeEnum;
import org.springframework.lang.NonNull;

import java.util.List;

public class CreateIndex implements DdlUpgrade {
    @NonNull
    private String indexName;
    @NonNull
    private String tableName;
    @NonNull
    private List<String> columnNames;
    private IndexTypeEnum indexType;

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

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

    public IndexTypeEnum getIndexType() {
        return this.indexType;
    }

    public void setIndexType(IndexTypeEnum indexType) {
        this.indexType = indexType;
    }
}
