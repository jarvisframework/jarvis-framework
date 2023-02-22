package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年11月18日
 */
public class SubEntityQuery<T extends Serializable> extends EntityQuery<T> implements ConditionExpression {

    @JsonIgnore
    private transient ComposedCondition<?> parent;

    /**
     *
     */
    private static final long serialVersionUID = 96826205088054147L;

    /**
     *
     * @see com.jarvis.framework.search.ConditionExpression#isSingleCondition()
     */
    @Override
    public boolean isSingleCondition() {
        return false;
    }

    public ComposedCondition<?> parent() {
        return parent;
    }
}
