package com.jarvis.framework.mybatis.update.data;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月28日
 */
public class CalcColumn {

    // 1: +, 2: -
    private Integer type;

    private String column;

    private Number value;

    public CalcColumn() {
    }

    /**
     * @param type
     * @param column
     * @param value
     */
    public CalcColumn(Integer type, String column, Number value) {
        super();
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

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the value
     */
    public Number getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Number value) {
        this.value = value;
    }

}
