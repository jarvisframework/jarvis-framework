package com.jarvis.framework.database.upgrade.model;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
public class RenameColumn implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 原字段名称
     */
    private String columnName;

    /**
     * 重命名后的字段信息，包括字段名称、数据类型、注释等
     */
    private Column newColumn;

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
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the newColumn
     */
    public Column getNewColumn() {
        return newColumn;
    }

    /**
     * @param newColumn the newColumn to set
     */
    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }

}
