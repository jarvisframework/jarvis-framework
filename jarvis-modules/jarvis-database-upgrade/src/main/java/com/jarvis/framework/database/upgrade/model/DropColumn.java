package com.jarvis.framework.database.upgrade.model;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public class DropColumn implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段名称集合，一个或多个
     */
    private List<String> columnNames;

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

}
