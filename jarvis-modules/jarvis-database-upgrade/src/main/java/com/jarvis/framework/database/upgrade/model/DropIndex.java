package com.jarvis.framework.database.upgrade.model;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public class DropIndex implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 索引名称
     */
    private String indexName;

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

}
