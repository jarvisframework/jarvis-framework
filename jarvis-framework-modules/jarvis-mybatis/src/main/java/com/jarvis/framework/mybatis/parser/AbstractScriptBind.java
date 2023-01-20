package com.jarvis.framework.mybatis.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScriptBind<Column, Criteria extends AbstractCriteriaCondition<Column, Criteria>, ScriptBind extends AbstractScriptBind<Column, Criteria, ScriptBind>> {
    protected final String bindName = "_param";
    protected String where;
    protected final List<Object> params = new ArrayList(8);
    protected Criteria criterion;

    public AbstractScriptBind() {
    }

    public String getWhere() {
        return this.where;
    }

    public List<Object> getParams() {
        return this.params;
    }

    protected void toWhere() {
        ComposedCondition<Column> filter = this.criterion.getFilter();
        String sql = ComposedConditionParser.newInstance("_param", this.params).parse(filter);
        this.where = sql;
    }

    public abstract ScriptBind parse();
}
