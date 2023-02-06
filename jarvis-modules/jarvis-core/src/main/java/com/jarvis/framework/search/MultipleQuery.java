package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月26日
 */
public class MultipleQuery extends CriteriaQuery<String> {

    private final List<JoinTable> joinTables = new ArrayList<>();

    private String alias;

    /**
     * 查询字段请加对应的表的别名，如：columns("t.id", "a.fonds_code", "t.code AS user_code")
     *
     */
    @Override
    public MultipleQuery columns(String... columns) {
        getColumns().addAll(Arrays.asList(columns));
        return this;
    }

    /**
     * @return the joinTables
     */
    public List<JoinTable> getJoinTables() {
        return joinTables;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    public JoinTable leftJoin() {
        final JoinTable joinTable = new JoinTable(this);
        joinTables.add(joinTable);
        return joinTable.leftJoin();
    }

    public JoinTable join() {
        final JoinTable joinTable = new JoinTable(this);
        joinTables.add(joinTable);
        return joinTable.join();
    }

    /**
     * 设置查询表名后，请设置对应的表别名，如 tableName("t_user").alias("u")
     *
     */
    @Override
    public MultipleQuery tableName(String tableName) {
        super.tableName(tableName);
        return this;
    }

    /**
     * 设置表别名
     *
     * @param alias 别名
     * @return
     */
    public MultipleQuery alias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.criteria.CriteriaQuery#filter(com.jarvis.framework.function.BuildFunction)
     */
    @Override
    public MultipleQuery filter(BuildFunction<ComposedCondition<String>> function) {
        super.filter(function);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#filter(java.util.function.Consumer)
     */
    @Override
    public MultipleQuery filter(Consumer<ComposedCondition<String>> condition) {
        super.filter(condition);
        return this;
    }

    /**
     * 字段名称，请带上表别名
     *
     * @see com.jarvis.framework.search.CriteriaQuery#columns(java.util.Collection)
     */
    @Override
    public MultipleQuery columns(Collection<String> columns) {
        super.columns(columns);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count()
     */
    @Override
    public MultipleQuery count() {
        super.count();
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#count(java.lang.String)
     */
    @Override
    public MultipleQuery count(String columnAlias) {
        super.count(columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object)
     */
    @Override
    public MultipleQuery countColumn(String column) {
        super.countColumn(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#countColumn(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery countColumn(String column, String columnAlias) {
        super.countColumn(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object)
     */
    @Override
    public MultipleQuery max(String column) {
        super.max(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#max(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery max(String column, String columnAlias) {
        super.max(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object)
     */
    @Override
    public MultipleQuery min(String column) {
        super.min(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#min(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery min(String column, String columnAlias) {
        super.min(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object)
     */
    @Override
    public MultipleQuery sum(String column) {
        super.sum(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#sum(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery sum(String column, String columnAlias) {
        super.sum(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object)
     */
    @Override
    public MultipleQuery avg(String column) {
        super.avg(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#avg(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery avg(String column, String columnAlias) {
        super.avg(column, columnAlias);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#asc(java.lang.Object)
     */
    @Override
    public MultipleQuery asc(String column) {
        super.asc(column);
        return this;
    }

    /**
     *
     * @see com.jarvis.framework.search.CriteriaQuery#desc(java.lang.Object)
     */
    @Override
    public MultipleQuery desc(String column) {
        super.desc(column);
        return this;
    }

    /**
     * @see com.jarvis.framework.search.CriteriaQuery#validate(java.lang.Object, java.lang.String)
     */
    @Override
    public MultipleQuery validate(String column, String errorMsg) {
        super.validate(column, errorMsg);
        return this;
    }

}
