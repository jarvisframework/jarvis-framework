package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.util.ColumnFunctionUtil;

/**
 * 单个条件，如：age=18
 * 泛型Column为String/String[]
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月13日
 */
public class SingleCondition<Column, Value> implements ConditionExpression {

    /**
     *
     */
    private static final long serialVersionUID = -5515604292849288470L;

    private Column column;

    private ConditionOperatorEnum operator;

    private Value value;

    /**
     *
     */
    public SingleCondition() {
        super();
    }

    /**
     * @param column
     * @param operator
     * @param value
     */
    public SingleCondition(Column column, ConditionOperatorEnum operator, Value value) {
        super();
        this.column = column;
        this.operator = operator;
        this.value = value;

    }

    /**
     * @return the column
     */
    public Column getColumn() {
        return column;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * @return the operator
     */
    public ConditionOperatorEnum getOperator() {
        return operator;
    }

    /**
     * @param operator
     *            the operator to set
     */
    public void setOperator(ConditionOperatorEnum operator) {
        this.operator = operator;
    }

    /**
     * @return the value
     */
    public Value getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     *
     * @see com.jarvis.framework.search.ConditionExpression#isSingleCondition()
     */
    @Override
    @JsonIgnore
    public boolean isSingleCondition() {
        return true;
    }

    /**
     * 是否为单字段条件(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public boolean isSingleColumn() {
        return (this.column instanceof String);
    }

    /**
     * 无值条件(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public boolean isNullValue() {
        return null == this.value;
    }

    /**
     * between and 值条件(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public boolean isBetweenValue() {
        return (this.value instanceof BetweenValue);
    }

    /**
     * 集合值条件(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public boolean isListValue() {
        return (this.value.getClass().isArray());
    }

    /**
     * 单值条件(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public boolean isSingleValue() {
        if (isNullValue() || isBetweenValue() || isListValue()) {
            return false;
        }
        return true;
    }

    /**
     * 数据库字段(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public Object getDbColumn() {
        if (this.column instanceof String) {
            return ColumnFunctionUtil.fieldToColumn(this.column.toString());
        }
        final String[] columns = (String[]) this.column;
        final int len = columns.length;
        final String[] fields = new String[len];
        for (int i = 0; i < len; i++) {
            fields[i] = ColumnFunctionUtil.fieldToColumn(columns[i]);
        }
        return fields;
    }

    /**
     * 数据库条件操作符(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public String getDbOperator() {
        return this.operator.getCode();
    }

    /**
     * 数据库条件值(Mybatis Mapper.xml中使用)
     *
     * @return
     */
    @JsonIgnore
    public Object getDbValue() {

        if (ConditionOperatorEnum.LIKE == this.operator) {
            return "%" + this.value + "%";
        }
        if (ConditionOperatorEnum.SW == this.operator) {
            return this.value + "%";
        }
        if (ConditionOperatorEnum.EW == this.operator) {
            return "%" + this.value;
        }

        return this.value;
    }

}
