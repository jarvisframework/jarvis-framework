package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.search.CriteriaQuery;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

public class ScriptBindQuery<Column> extends AbstractScriptBind<Column, CriteriaQuery<Column>, ScriptBindQuery<Column>> {
    private String columns;
    private String orderBy;
    private String groupBy;

    ScriptBindQuery(CriteriaQuery<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindQuery<Column> create(CriteriaQuery<Column> criterion) {
        return new ScriptBindQuery(criterion);
    }

    public ScriptBindQuery<Column> parse() {
        this.toSelectColumns();
        this.toWhere();
        this.toGroupBy();
        this.toOrderBy();
        return this;
    }

    protected void toSelectColumns() {
        this.columns = StringUtils.collectionToCommaDelimitedString(((CriteriaQuery) this.criterion).getColumns());
        if (!StringUtils.hasLength(this.columns)) {
            this.columns = "*";
        }

    }

    protected void toGroupBy() {
        this.groupBy = StringUtils.collectionToCommaDelimitedString(((CriteriaQuery) this.criterion).getGroupBy());
    }

    protected void toOrderBy() {
        // todo 编译错误
        /*this.orderBy = StringUtils.collectionToCommaDelimitedString((Collection) ((CriteriaQuery) this.criterion).getOrders().stream().map((o) -> {
            return o.getColumn() + " " + o.getOrderBy().name().toUpperCase();
        }).collect(Collectors.toList()));*/
    }

    public String getColumns() {
        return this.columns;
    }

    public String getGroupBy() {
        return this.groupBy;
    }

    public String getOrderBy() {
        return this.orderBy;
    }
}
