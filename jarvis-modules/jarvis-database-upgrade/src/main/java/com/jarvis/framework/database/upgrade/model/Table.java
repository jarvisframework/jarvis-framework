package com.jarvis.framework.database.upgrade.model;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月9日
 */
public class Table implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

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

}
