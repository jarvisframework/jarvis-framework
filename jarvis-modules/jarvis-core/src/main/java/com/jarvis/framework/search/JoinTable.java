package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;

import java.util.function.Consumer;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月26日
 */
public class JoinTable {

    private String join;

    private String tableName;

    private String alias;

    private String on;

    protected final ComposedCondition<String> filter = new ComposedCondition<String>();

    private final MultipleQuery query;

    JoinTable(MultipleQuery query) {
        this.query = query;
    }

    /**
     * LEFT JOIN
     *
     * @return
     */
    public JoinTable leftJoin() {
        this.join = " LEFT JOIN ";
        return this;
    }

    /**
     * JOIN
     *
     * @return
     */
    public JoinTable join() {
        this.join = " JOIN ";
        return this;
    }

    /**
     * 级联表名称，设置表名后，请设置对应的表别名，如 tableName("t_user").alias("u")
     *
     * @param tableName
     * @return
     */
    public JoinTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * 表别名，如：a
     *
     * @param alias
     * @return
     */
    public JoinTable alias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * 表关系条件：如t.id=a.t_id AND a.b_id=b.id
     *
     * @param on
     * @return
     */
    public JoinTable on(String on) {
        this.on = on;
        return this;
    }

    /**
     * 添加条件
     *
     * @param condition 条件
     * @return JoinTable
     */
    public JoinTable filter(BuildFunction<ComposedCondition<String>> condition) {
        condition.build(this.filter);
        return this;
    }

    /**
     * 添加条件
     *
     * @param condition 条件
     * @return JoinTable
     */
    public JoinTable filter(Consumer<ComposedCondition<String>> condition) {
        condition.accept(this.filter);
        return this;
    }

    public MultipleQuery end() {
        return query;
    }

    /**
     * @return the join
     */
    public String getJoin() {
        return join;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the on
     */
    public String getOn() {
        return on;
    }

    /**
     * @return the filter
     */
    public ComposedCondition<String> getFilter() {
        return filter;
    }

}
