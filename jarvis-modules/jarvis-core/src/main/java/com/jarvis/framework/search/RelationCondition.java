package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月18日
 */
public class RelationCondition implements ConditionExpression {

    /**
     *
     */
    private static final long serialVersionUID = -5515604292849288470L;

    private String column;

    private final ConditionOperatorEnum operator = ConditionOperatorEnum.EQ;

    private String value;

    /**
     *
     */
    public RelationCondition() {
        super();
    }

    /**
     * @param column
     * @param operator
     * @param value
     */
    public RelationCondition(String column, String value) {
        super();
        this.column = column;
        this.value = value;

    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the operator
     */
    public ConditionOperatorEnum getOperator() {
        return operator;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
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

}
