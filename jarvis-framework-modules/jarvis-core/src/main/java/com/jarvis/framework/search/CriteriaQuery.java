package com.jarvis.framework.search;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CriteriaQuery<Column> extends AbstractCriteriaCondition<Column, CriteriaQuery<Column>> {

    private final Set<String> columns = new LinkedHashSet(8);

    private final List<String> groupBy = new ArrayList(4);

    private final List<OrderBy> orders = new ArrayList(4);

    private final Map<String, String> validateMap = new HashMap();

    public Set<String> getColumns() {
        return this.columns;
    }

    public List<String> getGroupBy() {
        return this.groupBy;
    }

    public List<OrderBy> getOrders() {
        return this.orders;
    }

    public CriteriaQuery<Column> filter(BuildFunction<ComposedCondition<Column>> condition) {
        condition.build(this.filter);
        return this;
    }

    public CriteriaQuery<Column> filter(Consumer<ComposedCondition<Column>> condition) {
        condition.accept(this.filter);
        return this;
    }

    public CriteriaQuery<Column> columns(Column... columns) {
        this.columns.addAll((Collection)Arrays.asList(columns).stream().map((column) -> {
            return this.toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    public CriteriaQuery<Column> columns(Collection<Column> columns) {
        this.columns.addAll((Collection)columns.stream().map((column) -> {
            return this.toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    public CriteriaQuery<Column> count() {
        this.columns.add("count(1)");
        return this;
    }

    public CriteriaQuery<Column> count(String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("count(1) as " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> countColumn(Column column) {
        String col = this.toColumn(column);
        this.columns.add("count(" + col + ") as " + col);
        return this;
    }

    public CriteriaQuery<Column> countColumn(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("count(" + this.toColumn(column) + ") as " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> max(Column column) {
        String col = this.toColumn(column);
        this.columns.add("max(" + col + ") as " + col);
        return this;
    }

    public CriteriaQuery<Column> max(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("max(" + this.toColumn(column) + ") AS " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> min(Column column) {
        String col = this.toColumn(column);
        this.columns.add("min(" + col + ") AS " + col);
        return this;
    }

    public CriteriaQuery<Column> min(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("min(" + this.toColumn(column) + ") AS " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> sum(Column column) {
        String col = this.toColumn(column);
        this.columns.add("sum(" + col + ") AS " + col);
        return this;
    }

    public CriteriaQuery<Column> sum(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("sum(" + this.toColumn(column) + ") AS " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> avg(Column column) {
        String col = this.toColumn(column);
        this.columns.add("avg(" + col + ") AS " + col);
        return this;
    }

    public CriteriaQuery<Column> avg(Column column, String columnAlias) {
        Assert.hasText(columnAlias, "字段别名不能为空");
        this.columns.add("avg(" + this.toColumn(column) + ") AS " + columnAlias);
        return this;
    }

    public CriteriaQuery<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @SafeVarargs
    public final CriteriaQuery<Column> groupBy(Column... columns) {
        this.groupBy.addAll((Collection)Arrays.asList(columns).stream().map((column) -> {
            return this.toColumn(column);
        }).collect(Collectors.toList()));
        return this;
    }

    public CriteriaQuery<Column> asc(Column column) {
        this.orders.add(new OrderBy(this.toColumn(column), OrderByEnum.ASC));
        return this;
    }

    public CriteriaQuery<Column> desc(Column column) {
        this.orders.add(new OrderBy(this.toColumn(column), OrderByEnum.DESC));
        return this;
    }

    public CriteriaQuery<Column> validate(Column column, String errorMsg) {
        this.validateMap.put(this.toColumn(column), errorMsg);
        return this;
    }

    public void validate() {
        if (!this.validateMap.isEmpty()) {
            List<String> cols = new ArrayList();
            List<ConditionExpression> expressions = this.getFilter().getConditionExpressions();
            if (!expressions.isEmpty()) {
                this.validate((List)expressions, (List)cols);
                if (this.validateMap.values().size() != cols.size()) {
                    this.validateMap.forEach((k, v) -> {
                        if (!cols.contains(k)) {
                            throw new BusinessException(v);
                        }
                    });
                }

            }
        }
    }

    private void validate(List<ConditionExpression> expressions, List<String> existCols) {
        Iterator var3 = expressions.iterator();

        while(var3.hasNext()) {
            ConditionExpression expression = (ConditionExpression)var3.next();
            if (expression instanceof SingleCondition) {
                SingleCondition singleCondition = (SingleCondition)expression;
                String col = String.valueOf(singleCondition.getColumn());
                if (this.validateMap.containsKey(singleCondition.getColumn())) {
                    existCols.add(col);
                }
            } else {
                ComposedCondition compose = (ComposedCondition)expression;
                this.validate(compose.getConditionExpressions(), existCols);
            }
        }

    }
}
