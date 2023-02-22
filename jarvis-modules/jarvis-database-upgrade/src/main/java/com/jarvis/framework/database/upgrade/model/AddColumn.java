package com.jarvis.framework.database.upgrade.model;

import java.util.List;

/**
 * 字段新增
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月1日
 */
public class AddColumn implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 字段集合
     **/
    private List<Column> columns;

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
     * @return the columns
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}
