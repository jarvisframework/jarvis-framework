package com.jarvis.framework.database.upgrade.model;

import com.jarvis.framework.database.upgrade.constant.DataTypeEnum;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
public class Column {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 数据类型：VARCHAR/INT/BIGINT/DATE/DATETIME
     **/
    private DataTypeEnum dataType;

    /**
     * 长度
     **/
    private Integer length;

    /**
     * 标度
     **/
    private Integer scale;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否为空
     **/
    private boolean nullable = true;

    /**
     * 注释
     **/
    private String comment;

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
     * @return the dataType
     */
    public DataTypeEnum getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @return the scale
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
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

}
