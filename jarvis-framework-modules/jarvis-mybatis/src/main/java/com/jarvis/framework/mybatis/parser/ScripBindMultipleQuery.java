package com.jarvis.framework.mybatis.parser;

import org.springframework.util.StringUtils;

public class ScripBindMultipleQuery extends ScriptBindQuery<String> {
    private String join;
    private final String tableAlias;

    ScripBindMultipleQuery(MultipleQuery criterion) {
        super(criterion);
        this.tableAlias = criterion.getAlias();
    }

    public static ScripBindMultipleQuery create(MultipleQuery criterion) {
        return new ScripBindMultipleQuery(criterion);
    }

    public ScripBindMultipleQuery parse() {
        super.parse();
        this.toJoinSql();
        return this;
    }

    private void toJoinSql() {
        MultipleQuery query = (MultipleQuery)this.criterion;
        List<JoinTable> joinTables = query.getJoinTables();
        StringBuilder sql = new StringBuilder();
        joinTables.stream().map((jt) -> {
            return this.joinSql(jt);
        }).forEach((j) -> {
            sql.append(j).append("\n");
        });
        this.join = sql.toString();
    }

    private String joinSql(JoinTable joinTable) {
        StringBuilder sql = new StringBuilder();
        sql.append(joinTable.getJoin()).append(joinTable.getTableName()).append(" ").append(joinTable.getAlias()).append(" ON (").append(joinTable.getOn());
        String filter = this.filter(joinTable.getFilter());
        if (StringUtils.hasText(filter)) {
            sql.append(" AND ").append(filter);
        }

        sql.append(") ");
        return sql.toString();
    }

    private String filter(ComposedCondition<String> filter) {
        String sql = ComposedConditionParser.newInstance("_param", this.params).parse(filter);
        return sql;
    }

    public String getJoin() {
        return this.join;
    }

    public String getTableAlias() {
        return this.tableAlias;
    }
}
