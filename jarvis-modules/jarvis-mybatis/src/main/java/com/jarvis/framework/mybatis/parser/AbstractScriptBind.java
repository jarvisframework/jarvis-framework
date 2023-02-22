package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.search.ComposedCondition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月21日
 */
public abstract class AbstractScriptBind<Column,
        Criteria extends AbstractCriteriaCondition<Column, Criteria>,
        ScriptBind extends AbstractScriptBind<Column, Criteria, ScriptBind>> {

    protected final String bindName = ScriptBindConstant.BIND_NAME;

    protected String where;

    protected final List<Object> params = new ArrayList<Object>(8);

    protected Criteria criterion;

    /**
     * @return the where
     */
    public String getWhere() {
        return where;
    }

    /**
     * @return the params
     */
    public List<Object> getParams() {
        return params;
    }

    /**
     * WHERE条件解析
     */
    protected void toWhere() {
        final ComposedCondition<Column> filter = criterion.getFilter();
        final String sql = ComposedConditionParser.newInstance(bindName, params).parse(filter);
        this.where = sql;
    }

    public abstract ScriptBind parse();

}
