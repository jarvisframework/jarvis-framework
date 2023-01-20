package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class MultipleQuery extends CriteriaQuery<String> {
    private final List<JoinTable> joinTables = new ArrayList();
    private String alias;

    public MultipleQuery() {
    }

    public MultipleQuery columns(String... columns) {
        this.getColumns().addAll(Arrays.asList(columns));
        return this;
    }

    public List<JoinTable> getJoinTables() {
        return this.joinTables;
    }

    public String getAlias() {
        return this.alias;
    }

    public JoinTable leftJoin() {
        JoinTable joinTable = new JoinTable(this);
        this.joinTables.add(joinTable);
        return joinTable.leftJoin();
    }

    public JoinTable join() {
        JoinTable joinTable = new JoinTable(this);
        this.joinTables.add(joinTable);
        return joinTable.join();
    }

    public MultipleQuery tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    public MultipleQuery alias(String alias) {
        this.alias = alias;
        return this;
    }

    public MultipleQuery filter(BuildFunction<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    public MultipleQuery filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    public MultipleQuery columns(Collection<String> columns) {
        super.columns(columns);
        return this;
    }

    public MultipleQuery count() {
        super.count();
        return this;
    }

    public MultipleQuery count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    public MultipleQuery countColumn(String column) {
        super.countColumn(column);
        return this;
    }

    public MultipleQuery countColumn(String column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    public MultipleQuery max(String column) {
        super.max(column);
        return this;
    }

    public MultipleQuery max(String column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    public MultipleQuery min(String column) {
        super.min(column);
        return this;
    }

    public MultipleQuery min(String column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    public MultipleQuery sum(String column) {
        super.sum(column);
        return this;
    }

    public MultipleQuery sum(String column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    public MultipleQuery avg(String column) {
        super.avg(column);
        return this;
    }

    public MultipleQuery avg(String column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    public MultipleQuery asc(String column) {
        super.asc(column);
        return this;
    }

    public MultipleQuery desc(String column) {
        super.desc(column);
        return this;
    }

    public MultipleQuery validate(String column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }
}
