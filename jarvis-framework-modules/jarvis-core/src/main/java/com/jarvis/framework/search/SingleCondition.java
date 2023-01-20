package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.util.ColumnFunctionUtil;

public class SingleCondition<Column, Value> implements ConditionExpression {

    private static final long serialVersionUID = -5515604292849288470L;

    private Column column;

    private ConditionOperatorEnum operator;

    private Value value;

    public SingleCondition(Column column, ConditionOperatorEnum operator, Value value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Column getColumn() {
        return this.column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public ConditionOperatorEnum getOperator() {
        return this.operator;
    }

    public void setOperator(ConditionOperatorEnum operator) {
        this.operator = operator;
    }

    public Value getValue() {
        return this.value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @JsonIgnore
    public boolean isSingleCondition() {
        return true;
    }

    @JsonIgnore
    public boolean isSingleColumn() {
        return this.column instanceof String;
    }

    @JsonIgnore
    public boolean isNullValue() {
        return null == this.value;
    }

    @JsonIgnore
    public boolean isBetweenValue() {
        return this.value instanceof BetweenValue;
    }

    @JsonIgnore
    public boolean isListValue() {
        return this.value.getClass().isArray();
    }

    @JsonIgnore
    public boolean isSingleValue() {
        return !this.isNullValue() && !this.isBetweenValue() && !this.isListValue();
    }

    @JsonIgnore
    public Object getDbColumn() {
        if (this.column instanceof String) {
            return ColumnFunctionUtil.fieldToColumn(this.column.toString());
        } else {
            String[] columns = (String[])((String[])this.column);
            int len = columns.length;
            String[] fields = new String[len];

            for(int i = 0; i < len; ++i) {
                fields[i] = ColumnFunctionUtil.fieldToColumn(columns[i]);
            }

            return fields;
        }
    }

    @JsonIgnore
    public String getDbOperator() {
        return this.operator.getCode();
    }

    @JsonIgnore
    public Object getDbValue() {
        if (ConditionOperatorEnum.LIKE == this.operator) {
            return "%" + this.value + "%";
        } else if (ConditionOperatorEnum.SW == this.operator) {
            return this.value + "%";
        } else {
            return ConditionOperatorEnum.EW == this.operator ? "%" + this.value : this.value;
        }
    }
}
