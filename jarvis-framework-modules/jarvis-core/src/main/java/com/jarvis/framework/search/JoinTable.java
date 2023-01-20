package com.jarvis.framework.search;

import com.jarvis.framework.function.BuildFunction;

import java.util.function.Consumer;

public class JoinTable {
    private String join;
    private String tableName;
    private String alias;
    private String on;
    protected final ComposedCondition<String> filter = new ComposedCondition();
    private final MultipleQuery query;

    JoinTable(MultipleQuery query) {
        this.query = query;
    }

    public JoinTable leftJoin() {
        this.join = " LEFT JOIN ";
        return this;
    }

    public JoinTable join() {
        this.join = " JOIN ";
        return this;
    }

    public JoinTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public JoinTable alias(String alias) {
        this.alias = alias;
        return this;
    }

    public JoinTable on(String on) {
        this.on = on;
        return this;
    }

    public JoinTable filter(BuildFunction<ComposedCondition<String>> condition) {
        condition.build(this.filter);
        return this;
    }

    public JoinTable filter(Consumer<ComposedCondition<String>> condition) {
        condition.accept(this.filter);
        return this;
    }

    public MultipleQuery end() {
        return this.query;
    }

    public String getJoin() {
        return this.join;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getOn() {
        return this.on;
    }

    public ComposedCondition<String> getFilter() {
        return this.filter;
    }
}
