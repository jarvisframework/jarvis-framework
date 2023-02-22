package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.search.JoinTable;
import com.jarvis.framework.search.MultipleQuery;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月26日
 */
public class ScripBindMultipleQuery extends ScriptBindQuery<String> {

    private String join;

    private final String tableAlias;

    ScripBindMultipleQuery(MultipleQuery criterion) {
        super(criterion);
        tableAlias = criterion.getAlias();
    }

    public static ScripBindMultipleQuery create(MultipleQuery criterion) {
        return new ScripBindMultipleQuery(criterion);
    }

    @Override
    public ScripBindMultipleQuery parse() {

        //
        super.parse();
        //
        toJoinSql();

        return this;
    }

    private void toJoinSql() {
        final MultipleQuery query = (MultipleQuery) this.criterion;
        final List<JoinTable> joinTables = query.getJoinTables();
        final StringBuilder sql = new StringBuilder();
        joinTables.stream().map(jt -> this.joinSql(jt)).forEach(j -> {
            sql.append(j).append("\n");
        });
        this.join = sql.toString();
    }

    private String joinSql(JoinTable joinTable) {
        final StringBuilder sql = new StringBuilder();

        sql.append(joinTable.getJoin()).append(joinTable.getTableName()).append(SymbolConstant.SPACE)
                .append(joinTable.getAlias())
                .append(" ON (").append(joinTable.getOn());
        final String filter = filter(joinTable.getFilter());
        if (StringUtils.hasText(filter)) {
            sql.append(" AND ").append(filter);
        }
        sql.append(") ");

        return sql.toString();
    }

    private String filter(ComposedCondition<String> filter) {
        final String sql = ComposedConditionParser.newInstance(bindName, params).parse(filter);
        return sql;
    }

    /**
     * @return the join
     */
    public String getJoin() {
        return join;
    }

    /**
     * @return the tableAlias
     */
    public String getTableAlias() {
        return tableAlias;
    }

}
