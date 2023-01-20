package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComposedCondition<Column> implements ConditionExpression {
    private static final long serialVersionUID = -4606406829386240032L;
    private final LogicalOperatorEnum operator;
    private final List<ConditionExpression> conditionExpressions;
    @JsonIgnore
    private final transient ComposedCondition<Column> parent;

    public ComposedCondition() {
        this.operator = LogicalOperatorEnum.AND;
        this.conditionExpressions = new ArrayList(8);
        this.parent = this;
    }

    public ComposedCondition(LogicalOperatorEnum operator) {
        this.operator = operator;
        this.conditionExpressions = new ArrayList(8);
        this.parent = this;
    }

    public ComposedCondition(LogicalOperatorEnum operator, ComposedCondition<Column> parent) {
        this.operator = operator;
        this.conditionExpressions = new ArrayList(8);
        this.parent = parent;
    }

    public LogicalOperatorEnum getOperator() {
        return this.operator;
    }

    public List<ConditionExpression> getConditionExpressions() {
        return this.conditionExpressions;
    }

    public ComposedCondition<Column> getParent() {
        return this.parent;
    }

    private void add(ConditionExpression condition) {
        this.getConditionExpressions().add(condition);
    }

    private String toColumn(Column column) {
        return ColumnFunctionUtil.toDatabaseColumn(column);
    }

    public ComposedCondition<Column> equal(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.EQ, value));
        return this;
    }

    public ComposedCondition<Column> notEqual(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.NEQ, value));
        return this;
    }

    public ComposedCondition<Column> greaterThan(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.GT, value));
        return this;
    }

    public ComposedCondition<Column> lessThan(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.LT, value));
        return this;
    }

    public ComposedCondition<Column> greaterThanEqual(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.GTE, value));
        return this;
    }

    public ComposedCondition<Column> lessThanEqual(Column column, Object value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.LTE, value));
        return this;
    }

    public ComposedCondition<Column> between(Column column, Object startValue, Object endValue) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.BT, new BetweenValue(startValue, endValue)));
        return this;
    }

    public ComposedCondition<Column> isNull(Column column) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.NULL, (Object)null));
        return this;
    }

    public ComposedCondition<Column> isNotNull(Column column) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.NNULL, (Object)null));
        return this;
    }

    public ComposedCondition<Column> like(Column column, String value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    public ComposedCondition<Column> like(Column[] columns, String value) {
        this.add(new SingleCondition(((List)Stream.of(columns).map((column) -> {
            return this.toColumn(column);
        }).collect(Collectors.toList())).toArray(new String[columns.length]), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    public ComposedCondition<Column> like(List<Column> columns, String value) {
        this.add(new SingleCondition(((List)columns.stream().map((column) -> {
            return this.toColumn(column);
        }).collect(Collectors.toList())).toArray(new String[columns.size()]), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    public ComposedCondition<Column> startWidth(Column column, String value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.SW, value));
        return this;
    }

    public ComposedCondition<Column> endWidth(Column column, String value) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.EW, value));
        return this;
    }

    public <T> ComposedCondition<Column> in(Column column, List<T> values) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.IN, values.toArray()));
        return this;
    }

    public ComposedCondition<Column> in(Column column, Object... values) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.IN, values));
        return this;
    }

    public <T> ComposedCondition<Column> notIn(Column column, List<T> values) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.NIN, values.toArray()));
        return this;
    }

    public ComposedCondition<Column> notIn(Column column, Object... values) {
        this.add(new SingleCondition(this.toColumn(column), ConditionOperatorEnum.NIN, values));
        return this;
    }

    public ComposedCondition<Column> andSubCondition() {
        ComposedCondition<Column> ondComposedCondition = new ComposedCondition(LogicalOperatorEnum.AND, this);
        this.add(ondComposedCondition);
        return ondComposedCondition;
    }

    public ComposedCondition<Column> orSubCondition() {
        ComposedCondition<Column> orComposedCondition = new ComposedCondition(LogicalOperatorEnum.OR, this);
        this.add(orComposedCondition);
        return orComposedCondition;
    }

    public ComposedCondition<Column> endSubCondition() {
        return this.parent;
    }

    @JsonIgnore
    public boolean isSingleCondition() {
        return false;
    }
}
