package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.search.ComposedCondition;

import java.util.function.Consumer;

public class CriteriaDelete<Column> extends AbstractCriteriaCondition<Column, CriteriaDelete<Column>> {
    public CriteriaDelete() {
    }

    public CriteriaDelete<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CriteriaDelete<Column> filter(BuildFunction<ComposedCondition<Column>> function) {
        function.build(this.filter);
        return this;
    }

    public CriteriaDelete<Column> filter(Consumer<ComposedCondition<Column>> function) {
        function.accept(this.filter);
        return this;
    }
}
