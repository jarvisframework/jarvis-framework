package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.mybatis.update.CriteriaDelete;

public class ScriptBindDelete<Column> extends AbstractScriptBind<Column, CriteriaDelete<Column>, ScriptBindDelete<Column>> {
    private ScriptBindDelete(CriteriaDelete<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindDelete<Column> create(CriteriaDelete<Column> criterion) {
        return new ScriptBindDelete(criterion);
    }

    public ScriptBindDelete<Column> parse() {
        this.toWhere();
        return this;
    }
}
