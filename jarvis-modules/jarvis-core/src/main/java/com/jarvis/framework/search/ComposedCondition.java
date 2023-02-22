package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 组合条件（数据库、Elasticsearch、Solr）统一条件
 *
 * 如
 *
 * 1. name='小甜甜' and age=18
 *
 * <pre>
 * new ComposedCondition<String>().equal("name", "小甜甜").equal("age", 18)
 * </pre>
 *
 * 2. gender='女' and (name='小甜甜' or age between 18 and 20) and deleted=0
 *
 * <pre>
 * new ComposedCondition<String>()
 *     .equal("gender", "女")
 *     .orCondition().like("name", "小甜甜").between("age", 18, 20).end()
 *     .equal("deleted", 0)
 * </pre>
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月13日
 */
public class ComposedCondition<Column> implements ConditionExpression {

    /**
     *
     */
    private static final long serialVersionUID = -4606406829386240032L;

    private final LogicalOperatorEnum operator;

    private final List<ConditionExpression> conditionExpressions;

    @JsonIgnore
    private transient final ComposedCondition<Column> parent;

    /**
     *
     */
    public ComposedCondition() {
        super();
        this.operator = LogicalOperatorEnum.AND;
        this.conditionExpressions = new ArrayList<ConditionExpression>(8);
        this.parent = this;
    }

    /**
     *
     */
    public ComposedCondition(LogicalOperatorEnum operator) {
        super();
        this.operator = operator;
        this.conditionExpressions = new ArrayList<ConditionExpression>(8);
        this.parent = this;
    }

    /**
     *
     */
    public ComposedCondition(LogicalOperatorEnum operator, ComposedCondition<Column> parent) {
        super();
        this.operator = operator;
        this.conditionExpressions = new ArrayList<ConditionExpression>(8);
        this.parent = parent;
    }

    /**
     * @return the operator
     */
    public LogicalOperatorEnum getOperator() {
        return operator;
    }

    /**
     * @return the conditionExpressions
     */
    public List<ConditionExpression> getConditionExpressions() {
        return conditionExpressions;
    }

    /**
     * @param operator the operator to set
     */
    /*public void setOperator(LogicalOperatorEnum operator) {
        this.operator = operator;
    }*/

    /**
     * @param conditionExpressions the conditionExpressions to set
     */
    /*public void setConditionExpressions(List<ConditionExpression> conditionExpressions) {
        this.conditionExpressions = conditionExpressions;
    }*/

    /**
     * @return the parent
     */
    public ComposedCondition<Column> getParent() {
        return parent;
    }

    /**
     * 创建一组or条件，如 name='a' or age=18 or ...
     *
     * @return
     */
    /*public static ComposedCondition newOrComposedCondition() {
        final ComposedCondition composedCondition = new ComposedCondition(LogicalOperatorEnum.OR);
        return composedCondition;
    }*/

    /**
     * 创建一组and条件，如 name='a' and age=18 and ...
     *
     * @return
     */
    /*public static ComposedCondition newAndComposedCondition() {
        final ComposedCondition composedCondition = new ComposedCondition();
        return composedCondition;
    }*/

    private void add(ConditionExpression condition) {
        getConditionExpressions().add(condition);
    }

    private String toColumn(Column column) {
        /*if (String.class.isAssignableFrom(column.getClass())) {
            return (String) column;
        }
        if (SerializableFunction.class.isAssignableFrom(column.getClass())) {
            return SerializableFunctionUtil.getFieldName((SerializableFunction<?, ?>) column);
        }*/
        return ColumnFunctionUtil.toDatabaseColumn(column);
    }

    /**
     * 等于，示例filter.equal("fonds_code", "SH001")或filter.equal(ArchiveType::getFondsCode, "SH001")
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     *
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> equal(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.EQ, value));
        return this;
    }

    /**
     * 不等于，示例filter.equal("fonds_code", "SH001")或filter.equal(ArchiveType::getFondsCode, "SH001")
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> notEqual(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.NEQ, value));
        return this;
    }

    /**
     * 大于
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> greaterThan(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.GT, value));
        return this;
    }

    /**
     * 小于
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> lessThan(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.LT, value));
        return this;
    }

    /**
     * 大等于
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> greaterThanEqual(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.GTE, value));
        return this;
    }

    /**
     * 小等于
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> lessThanEqual(Column column, Object value) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.LTE, value));
        return this;
    }

    /**
     * 介于，如：age between 18 and 20
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> between(Column column, Object startValue, Object endValue) {
        add(new SingleCondition<String, BetweenValue>(toColumn(column), ConditionOperatorEnum.BT,
                new BetweenValue(startValue, endValue)));
        return this;
    }

    /**
     * 为空，如：age is null
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> isNull(Column column) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.NULL, null));
        return this;
    }

    /**
     * 为空，如：age is null
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> isNotNull(Column column) {
        add(new SingleCondition<String, Object>(toColumn(column), ConditionOperatorEnum.NNULL, null));
        return this;
    }

    /**
     * 模糊匹配，如：name like '%甜%'
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> like(Column column, String value) {
        add(new SingleCondition<String, String>(toColumn(column), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    /**
     * 模糊匹配，如：(name like '%甜%' or remark like '%甜%' or ...)
     *
     * @param value 值
     * @param columns 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @return
     */
    public ComposedCondition<Column> like(Column[] columns, String value) {

        add(new SingleCondition<String[], String>(Stream.of(columns).map(column -> {
            return toColumn(column);
        }).collect(Collectors.toList()).toArray(new String[columns.length]), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    /**
     * 模糊匹配，如：(name like '%甜%' or remark like '%甜%' or ...)
     *
     * @param columns 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> like(List<Column> columns, String value) {

        add(new SingleCondition<String[], String>(columns.stream().map(column -> {
            return toColumn(column);
        }).collect(Collectors.toList()).toArray(new String[columns.size()]), ConditionOperatorEnum.LIKE, value));
        return this;
    }

    /**
     * 模糊匹配，如：name like '甜%'
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> startWidth(Column column, String value) {
        add(new SingleCondition<String, String>(toColumn(column), ConditionOperatorEnum.SW, value));
        return this;
    }

    /**
     * 模糊匹配，如：name like '%甜'
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param value 值
     * @return
     */
    public ComposedCondition<Column> endWidth(Column column, String value) {
        add(new SingleCondition<String, String>(toColumn(column), ConditionOperatorEnum.EW, value));
        return this;
    }

    /**
     * 属于，如：age in (18, 20, 22)
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param values 值
     * @return
     */
    public <T> ComposedCondition<Column> in(Column column, List<T> values) {
        add(new SingleCondition<String, Object[]>(toColumn(column), ConditionOperatorEnum.IN, values.toArray()));
        return this;
    }

    /**
     * 属于，如：age in (18, 20, 22)
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param values 值
     * @return
     */
    public ComposedCondition<Column> in(Column column, Object... values) {
        add(new SingleCondition<String, Object[]>(toColumn(column), ConditionOperatorEnum.IN, values));
        return this;
    }

    /**
     * 不属于，如：age not in (18, 20, 22)
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param values 值
     * @return
     */
    public <T> ComposedCondition<Column> notIn(Column column, List<T> values) {
        add(new SingleCondition<String, Object[]>(toColumn(column), ConditionOperatorEnum.NIN, values.toArray()));
        return this;
    }

    /**
     * 不属于，如：age not in (18, 20, 22)
     *
     * @param column 字段名称或get方法，如fonds_code/ArchiveType::getFondsCode
     * @param values 值
     * @return
     */
    public ComposedCondition<Column> notIn(Column column, Object... values) {
        add(new SingleCondition<String, Object[]>(toColumn(column), ConditionOperatorEnum.NIN, values));
        return this;
    }

    /**
     * 添加一组and条件，如：status=1 or (code='350425' and city='sm') or ....
     *
     * 其中 (code='350425' and age>18) 这个就是一组and条件
     *
     * @return
     */
    public ComposedCondition<Column> andSubCondition() {
        final ComposedCondition<Column> ondComposedCondition = new ComposedCondition<Column>(LogicalOperatorEnum.AND,
                this);
        add(ondComposedCondition);
        return ondComposedCondition;
    }

    /**
     * 添加一组or条件，如：status=1 and (code='350425' or city='sm') and ....
     *
     * 其中 (code='350425' or city='sm') 这个就是一组or条件
     *
     * @return
     */
    public ComposedCondition<Column> orSubCondition() {
        final ComposedCondition<Column> orComposedCondition = new ComposedCondition<Column>(LogicalOperatorEnum.OR,
                this);
        add(orComposedCondition);
        return orComposedCondition;
    }

    /**
     * 表示当前组合条件拼接完成，返回到上一个组合条件位置，如：
     *
     * <pre>
     * status=1 and (name like 'Doug Wang' or code='350425') and deleted=0
     *
     * ComposedCondition<String> condition = new ComposedCondition<String>();
     * condition.equal("status", 1)
     *      .orCondition().like("name", "Doug Wang").equal("code", "350425").endSubCondition()
     *      .equal("deleted", 0)
     * </pre>
     *
     * @return
     */
    public ComposedCondition<Column> endSubCondition() {
        return this.parent;
    }

    /**
     *
     * @see com.jarvis.framework.search.ConditionExpression#isSingleCondition()
     */
    @Override
    @JsonIgnore
    public boolean isSingleCondition() {
        return false;
    }

    /**
     * 给当前所有条件添加别名
     *
     * @param alias 别名
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void processColumnAlias(String alias) {
        conditionExpressions.stream().forEach(ce -> {
            if (ce instanceof SingleCondition) {
                final Object colObj = ((SingleCondition) ce).getColumn();
                if (colObj instanceof String) {
                    ((SingleCondition) ce).setColumn(alias + SymbolConstant.DOT + colObj);
                } else {
                    final String[] cols = (String[]) colObj;
                    for (int i = 0, len = cols.length; i < len; i++) {
                        cols[i] = alias + SymbolConstant.DOT + cols[i];
                    }
                }
            } else if (ce instanceof ComposedCondition) {
                ((ComposedCondition) ce).processColumnAlias(alias);
            }
        });
    }
}
