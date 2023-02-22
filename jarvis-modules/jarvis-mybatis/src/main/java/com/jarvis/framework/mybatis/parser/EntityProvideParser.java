package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.mybatis.update.CriteriaDelete;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.CriteriaQueryBuilder;
import com.jarvis.framework.search.MultipleQuery;

import java.util.Map;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月15日
 */
public class EntityProvideParser {

    public static ScriptBindUpdate<?> parseUpdate(CriteriaUpdate<?> criterion) {
        return ScriptBindUpdate.create(criterion).parse();
    }

    public static ScriptBindDelete<?> parseDelete(CriteriaDelete<?> criterion) {
        return ScriptBindDelete.create(criterion).parse();
    }

    public static ScriptBindQuery<?> parseQuery(CriteriaQuery<?> criterion) {
        if (criterion instanceof MultipleQuery) {
            return ScripBindMultipleQuery.create((MultipleQuery) criterion).parse();
        }
        return ScriptBindQuery.create(criterion).parse();
    }

    public static ScriptBindQuery<?> parseQuery(Map<String, Object> parameter) {
        final CriteriaQuery<?> criterion = (CriteriaQuery<?>) parameter.get("criterion");
        if (criterion instanceof MultipleQuery) {
            return ScripBindMultipleQuery.create((MultipleQuery) criterion).parse();
        }
        return ScriptBindQuery.create(criterion).parse();
    }

    public static void main(String[] args) {

        final CriteriaQuery<String> critiaQuery = CriteriaQueryBuilder.createDynamicCriterion();

        critiaQuery.columns("id", "fonds_code", "year_code")
                .asc("archive_code")
                .tableName("t_ar_wsda_file")
                .filter(condition -> {
                    condition.equal("is_delete", "0");
                    final ComposedCondition<String> orSubCondition = condition.orSubCondition();
                    orSubCondition.andSubCondition()
                            .equal("idcard_number1", "xxxx").equal("name1", "aaaa")
                            .endSubCondition();
                    orSubCondition.andSubCondition()
                            .equal("idcard_number2", "xxxx").equal("name2", "aaaa")
                            .endSubCondition();
                    return condition;
                })
                .groupBy("userId", "year_code")
                .asc("createdTime")
                .desc("userId");

        final ScriptBindQuery<String> parse = ScriptBindQuery.<String>create(critiaQuery).parse();
        System.out.println(parse.getColumns());
        System.out.println(parse.getWhere());
        System.out.println(parse.getGroupBy());
        System.out.println(parse.getOrderBy());
    }
}
