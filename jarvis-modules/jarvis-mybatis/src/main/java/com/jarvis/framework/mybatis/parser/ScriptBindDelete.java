package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.mybatis.update.CriteriaDelete;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
public class ScriptBindDelete<Column>
        extends AbstractScriptBind<Column, CriteriaDelete<Column>, ScriptBindDelete<Column>> {

    private ScriptBindDelete(CriteriaDelete<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindDelete<Column> create(CriteriaDelete<Column> criterion) {
        return new ScriptBindDelete<Column>(criterion);
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.parser.AbstractScriptBind#parse()
     */
    @Override
    public ScriptBindDelete<Column> parse() {
        toWhere();
        return this;
    }

}
