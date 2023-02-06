package com.jarvis.framework.database.upgrade.model;

import com.jarvis.framework.database.upgrade.constant.IndexTypeEnum;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
public class CreateIndex implements DdlUpgrade {

    /**
     * 索引名称
     */
    @NonNull
    private String indexName;

    /**
     * 表名
     */
    @NonNull
    private String tableName;

    /**
     * 创建索引的字段集，一个或多个
     */
    @NonNull
    private List<String> columnNames;

    /**
     * 索引类型：UNIQUE/NORMAL
     */
    private IndexTypeEnum indexType;

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the columnNames
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * @param columnNames the columnNames to set
     */
    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * @return the indexType
     */
    public IndexTypeEnum getIndexType() {
        return indexType;
    }

    /**
     * @param indexType the indexType to set
     */
    public void setIndexType(IndexTypeEnum indexType) {
        this.indexType = indexType;
    }

}
