package com.jarvis.framework.mybatis.update.data;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月28日
 */
public class ColumnValue {

    /*true-表示value属性存放的是值;false-表示value属性存放的是字段*/
    private boolean valuable;

    private Object value;

    public ColumnValue() {

    }

    /**
     * @param isValue
     * @param value
     */
    public ColumnValue(boolean isValue, Object value) {
        super();
        this.valuable = isValue;
        this.value = value;
    }

    /**
     * 创建值对象
     *
     * @param value 值
     * @return ColumnValue
     */
    public static ColumnValue newValue(Object value) {
        return new ColumnValue(true, value);
    }

    /**
     * 创建字段对象
     *
     * @param column 字段
     * @return ColumnValue
     */
    public static ColumnValue newColumn(String column) {
        return new ColumnValue(false, column);
    }

    /**
     * @return the isValue
     */
    public boolean isValue() {
        return isValuable();
    }

    /**
     * @return the isValue
     */
    public boolean isValuable() {
        return valuable;
    }

    /**
     * @param isValue the isValue to set
     */
    public void setValuable(boolean isValue) {
        this.valuable = isValue;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
