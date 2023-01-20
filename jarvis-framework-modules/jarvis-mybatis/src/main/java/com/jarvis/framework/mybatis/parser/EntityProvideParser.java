package com.jarvis.framework.mybatis.parser;

public class EntityProvideParser {

    public static ScriptBindUpdate<?> parseUpdate(CriteriaUpdate<?> criterion) {
        return ScriptBindUpdate.create(criterion).parse();
    }

    public static ScriptBindDelete<?> parseDelete(CriteriaDelete<?> criterion) {
        return ScriptBindDelete.create(criterion).parse();
    }

    public static ScriptBindQuery<?> parseQuery(CriteriaQuery<?> criterion) {
        return (ScriptBindQuery) (criterion instanceof MultipleQuery ? ScripBindMultipleQuery.create((MultipleQuery) criterion).parse() : ScriptBindQuery.create(criterion).parse());
    }

    public static ScriptBindQuery<?> parseQuery(Map<String, Object> parameter) {
        CriteriaQuery<?> criterion = (CriteriaQuery) parameter.get("criterion");
        return (ScriptBindQuery) (criterion instanceof MultipleQuery ? ScripBindMultipleQuery.create((MultipleQuery) criterion).parse() : ScriptBindQuery.create(criterion).parse());
    }

    public static void main(String[] args) {
        CriteriaQuery<String> critiaQuery = CriteriaQueryBuilder.createDynamicCriterion();
        critiaQuery.columns(new String[]{"id", "fonds_code", "year_code"}).asc("archive_code").tableName("t_ar_wsda_file").filter((condition) -> {
            condition.equal("is_delete", "0");
            ComposedCondition<String> orSubCondition = condition.orSubCondition();
            orSubCondition.andSubCondition().equal("idcard_number1", "xxxx").equal("name1", "aaaa").endSubCondition();
            orSubCondition.andSubCondition().equal("idcard_number2", "xxxx").equal("name2", "aaaa").endSubCondition();
            return condition;
        }).groupBy(new String[]{"userId", "year_code"}).asc("createdTime").desc("userId");
        ScriptBindQuery<String> parse = ScriptBindQuery.create(critiaQuery).parse();
        System.out.println(parse.getColumns());
        System.out.println(parse.getWhere());
        System.out.println(parse.getGroupBy());
        System.out.println(parse.getOrderBy());
    }
}
