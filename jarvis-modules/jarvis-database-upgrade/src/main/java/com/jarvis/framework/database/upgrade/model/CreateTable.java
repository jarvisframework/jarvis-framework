package com.jarvis.framework.database.upgrade.model;

import java.util.List;

public class CreateTable implements DdlUpgrade {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 注释
     */
    private String comment;

    /**
     * 字段信息集合
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
