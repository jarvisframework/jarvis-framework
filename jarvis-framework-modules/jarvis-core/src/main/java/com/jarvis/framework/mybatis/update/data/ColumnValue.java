package com.jarvis.framework.mybatis.update.data;

public class ColumnValue {

    private boolean isValue;

    private Object value;

    public ColumnValue(boolean isValue, String value) {
        this.isValue = isValue;
        this.value = value;
    }

    public static ColumnValue newValue(String value) {
        return new ColumnValue(true, value);
    }

    public static ColumnValue newColumn(String column) {
        return new ColumnValue(false, column);
    }

    public boolean isValue() {
        return this.isValue;
    }

    public void setIsValue(boolean isValue) {
        this.isValue = isValue;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
