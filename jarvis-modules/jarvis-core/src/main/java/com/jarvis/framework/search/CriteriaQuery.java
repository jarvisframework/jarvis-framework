package com.jarvis.framework.search;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月13日
 */
public class CriteriaQuery<Column> extends AbstractCriteriaCondition<Column, CriteriaQuery<Column>> {

    /** 查询字段 */
    private final Set<Object> columns = new LinkedHashSet<Object>(8);

    private final List<String> groupBy = new ArrayList<String>(4);

    private final List<OrderBy> orders = new ArrayList<OrderBy>(4);

    private final Map<String, String> validateMap = new HashMap<>();

    private Highlight highlight;

    /**
     * @return the columns
     */
    public Set<Object> getColumns() {
        return columns;
    }

    public List<String> getGroupBy() {
        return this.groupBy;
    }

    /**
     * @return the orders
     */
    public List<OrderBy> getOrders() {
        return orders;
    }

    /**
     * @return the highlight
     */
    public Highlight getHighlight() {
        return highlight;
    }

    @Override
    public CriteriaQuery<Column> filter(BuildFunction<ComposedCondition<Column>> condition) {
        condition.build(this.filter);
        return this;
    }

    @Override
    public CriteriaQuery<Column> filter(Consumer<ComposedCondition<Column>> condition) {
        condition.accept(this.filter);
        return this;
    }

    /**
     * 添加查询字段（可以是字段名称与可以字段名称对应的驼峰属性），如：fonds_code/fondsCode
     *
     * @param columns
     * @return
     */
    public CriteriaQuery<Column> columns(Column... columns) {
        this.columns.addAll(Arrays.asList(columns).stream().map(column -> {
            return toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    /**
     * 添加查询字段
     *
     * @param columns
     * @return
     */
    public CriteriaQuery<Column> columns(Collection<Column> columns) {
        this.columns.addAll(columns.stream().map(column -> {
            return toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    /**
     * count(1)
     *
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> count() {
        this.columns.add(FunctionValue.create("count").addValueParam(1));
        return this;
    }

    /**
     * count(1) as columnAlias
     *
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> count(String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add(FunctionValue.create("count").addValueParam(1).alias(columnAlias));
        return this;
    }

    /**
     * count(column)
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> countColumn(Column column) {
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("count").addColumnParam(col));
        return this;
    }

    /**
     * count(column) as columnAlias
     *
     * @param column
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> countColumn(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("count").addColumnParam(col).alias(columnAlias));
        return this;
    }

    /**
     * max(column)
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> max(Column column) {
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("max").addColumnParam(col));
        return this;
    }

    /**
     * max(column) as columnAlias
     *
     * @param column
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> max(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("max").addColumnParam(col).alias(columnAlias));
        return this;
    }

    /**
     * min(column)
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> min(Column column) {
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("min").addColumnParam(col));
        return this;
    }

    /**
     * min(column) as columnAlias
     *
     * @param column
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> min(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("min").addColumnParam(col).alias(columnAlias));
        return this;
    }

    /**
     * sum(column)
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> sum(Column column) {
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("sum").addColumnParam(col));
        return this;
    }

    /**
     * sum(column) as columnAlias
     *
     * @param column
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> sum(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("sum").addColumnParam(col).alias(columnAlias));
        return this;
    }

    /**
     * avg(column)
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> avg(Column column) {
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("avg").addColumnParam(col));
        return this;
    }

    /**
     * avg(column) as columnAlias
     *
     * @param column
     * @param columnAlias
     * @return
     */
    public CriteriaQuery<Column> avg(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        final String col = toColumn(column);
        this.columns.add(FunctionValue.create("avg").addColumnParam(col).alias(columnAlias));
        return this;
    }

    /**
     * 设置查询表名
     *
     * @param tableName
     * @return
     */
    @Override
    public CriteriaQuery<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * 添加group by字段（可以是字段名称与可以字段名称对应的驼峰属性），如：fonds_code/类::getFondsCode
     *
     * @param columns
     * @return
     */
    @SafeVarargs
    public final CriteriaQuery<Column> groupBy(Column... columns) {
        this.groupBy.addAll(Arrays.asList(columns).stream().map(column -> {
            return toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    /**
     * 添加升序排序字段
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> asc(Column column) {
        this.orders.add(new OrderBy(toColumn(column), OrderByEnum.ASC));
        return this;
    }

    /**
     * 添加降序排序字段
     *
     * @param column
     * @return
     */
    public CriteriaQuery<Column> desc(Column column) {
        this.orders.add(new OrderBy(toColumn(column), OrderByEnum.DESC));
        return this;
    }

    /**
     * 添加必需的过虑字段
     *
     * @param column
     * @param errorMsg 如果不存在时，提示内容
     * @return
     */
    public CriteriaQuery<Column> validate(Column column, String errorMsg) {
        this.validateMap.put(toColumn(column), errorMsg);
        return this;
    }

    /**
     * 添加必需的过虑字段
     *
     * @param column
     * @param errorMsg 如果不存在时，提示内容
     * @return
     */
    public CriteriaQuery<Column> highlight(Highlight highlight) {
        this.highlight = highlight;
        return this;
    }

    /**
     * 执行校验
     */
    public void validate() {

        if (validateMap.isEmpty()) {
            return;
        }

        final List<String> cols = new ArrayList<>();

        final List<ConditionExpression> expressions = getFilter().getConditionExpressions();
        if (expressions.isEmpty()) {
            return;
        }
        validate(expressions, cols);
        if (validateMap.values().size() != cols.size()) {
            validateMap.forEach((k, v) -> {
                if (!cols.contains(k)) {
                    throw new BusinessException(v);
                }
            });
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void validate(List<ConditionExpression> expressions, List<String> existCols) {
        for (final ConditionExpression expression : expressions) {
            if (expression instanceof SingleCondition) {
                final SingleCondition singleCondition = (SingleCondition) expression;
                final String col = String.valueOf(singleCondition.getColumn());
                if (validateMap.containsKey(singleCondition.getColumn())) {
                    existCols.add(col);
                }
            } else {
                final ComposedCondition compose = (ComposedCondition) expression;
                validate(compose.getConditionExpressions(), existCols);
            }
        }
    }
}
