package com.jarvis.framework.mybatis.update.data;

public class CalcColumn {

    private Integer type;

    private String column;

    private Number value;

    public CalcColumn(Integer type, String column, Number value) {
        this.type = type;
        this.column = column;
        this.value = value;
    }

    public static CalcColumn plus(String column, Number value) {
        return new CalcColumn(1, column, value);
    }

    public static CalcColumn minus(String column, Number value) {
        return new CalcColumn(2, column, value);
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Number getValue() {
        return this.value;
    }

    public void setValue(Number value) {
        this.value = value;
    }
}
