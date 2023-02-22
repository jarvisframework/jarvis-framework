package com.jarvis.framework.database.upgrade.model;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public class RenameTable implements DdlUpgrade {

    /**
     * 原表名
     */
    private String tableName;

    /**
     * 新表名
     */
    private String newTableName;

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
     * @return the newTableName
     */
    public String getNewTableName() {
        return newTableName;
    }

    /**
     * @param newTableName the newTableName to set
     */
    public void setNewTableName(String newTableName) {
        this.newTableName = newTableName;
    }

}
