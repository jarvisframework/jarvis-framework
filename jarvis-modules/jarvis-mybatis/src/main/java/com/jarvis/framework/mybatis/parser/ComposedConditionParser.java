package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.util.ConditionParserUtil;
import com.jarvis.framework.search.BetweenValue;
import com.jarvis.framework.search.ComposedCondition;
import com.jarvis.framework.search.ConditionExpression;
import com.jarvis.framework.search.ConditionOperatorEnum;
import com.jarvis.framework.search.LogicalOperatorEnum;
import com.jarvis.framework.search.SingleCondition;

import java.util.List;

/**
 * 解析查询条件
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
public final class ComposedConditionParser {

    private final String bindName;

    private final List<Object> params;

    private ComposedConditionParser(String bindName, List<Object> params) {
        this.bindName = bindName;
        this.params = params;
    }

    public static ComposedConditionParser newInstance(String bindName, List<Object> params) {
        return new ComposedConditionParser(bindName, params);
    }

    /**
     * 解析查询条件
     *
     * @param composedCondition 条件
     * @return SQL语句
     */
    @SuppressWarnings("unchecked")
    public String parse(ComposedCondition<?> composedCondition) {
        if (null == composedCondition) {
            return "";
        }
        final StringBuilder sql = new StringBuilder(64);
        final List<ConditionExpression> conditionExpressions = composedCondition.getConditionExpressions();

        ConditionExpression conditionExpression;
        for (int i = 0, len = conditionExpressions.size(); i < len; i++) {
            conditionExpression = conditionExpressions.get(i);
            if (null == conditionExpression) {
                continue;
            }
            if (i > 0) {
                sql.append(getLogicalOperator(composedCondition.getOperator()));
            }
            if (conditionExpression.isSingleCondition()) {
                final SingleCondition<Object, Object> c = (SingleCondition<Object, Object>) conditionExpression;
                sql.append(parseSingleCondition(c));
            } else {
                final ComposedCondition<?> c = (ComposedCondition<?>) conditionExpression;
                sql.append(SymbolConstant.OPEN_PARENTHESIS)
                        .append(parse(c))
                        .append(SymbolConstant.CLOSE_PARENTHESIS);
            }
        }

        return sql.toString();
    }

    private String getLogicalOperator(LogicalOperatorEnum op) {
        return SymbolConstant.SPACE + op.name().toUpperCase() + SymbolConstant.SPACE;
    }

    private String parseSingleCondition(SingleCondition<Object, Object> condition) {
        final ConditionOperatorEnum operator = condition.getOperator();
        switch (operator) {
            case EQ:
                return toEqCondition(condition);
            case NEQ:
                return toNeqCondition(condition);
            case GT:
                return toGtCondition(condition);
            case GTE:
                return toGteCondition(condition);
            case LT:
                return toLtCondition(condition);
            case LTE:
                return toLteCondition(condition);
            case BT:
                return toBtCondition(condition);
            case LIKE:
                return toLikeCondition(condition);
            case SW:
                return toSwCondition(condition);
            case EW:
                return toEwCondition(condition);
            case IN:
                return toInCondition(condition);
            case NIN:
                return toNinCondition(condition);
            case NULL:
                return toNullCondition(condition);
            case NNULL:
                return toNnullCondition(condition);
            default:
                throw new FrameworkException("不支持操作符[" + operator.getName() + "]");
        }
    }

    /**
     * 转成 is not null条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toNnullCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append(" IS NOT NULL ");
        return sql.toString();
    }

    /**
     * 转成 is null条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toNullCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append(" IS NULL ");
        return sql.toString();
    }

    /**
     * 转成 not in() 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toNinCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        /*sql.append(column(condition)).append(" NOT IN \n")
            .append(getBindingField(condition.getValue()));
        sql.append("<foreach collection=\"").append(getBindingField(condition.getValue()))
            .append("\" item=\"val\" separator=\",\" open=\"(\" close=\")\"> \\n");
        sql.append(PersistentUtil.getBindingField("val"));
        sql.append("</foreach>\n");*/
        sql.append(column(condition)).append(" NOT IN (");
        final Object[] values = (Object[]) condition.getValue();
        for (int i = 0, len = values.length; i < len; i++) {
            if (i > 0) {
                sql.append(SymbolConstant.COMMA);
            }
            sql.append(getBindingField(values[i]));
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 转成 in() 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toInCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append(" IN (");
        final Object[] values = (Object[]) condition.getValue();
        for (int i = 0, len = values.length; i < len; i++) {
            if (i > 0) {
                sql.append(SymbolConstant.COMMA);
            }
            sql.append(getBindingField(values[i]));
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 转成 like 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toLikeCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        final Object column = condition.getColumn();
        final String value = "%" + condition.getValue() + "%";
        if (column instanceof String[]) {
            final String[] arrayColumn = (String[]) column;
            sql.append(SymbolConstant.OPEN_PARENTHESIS);
            for (int i = 0, len = arrayColumn.length; i < len; i++) {
                if (i > 0) {
                    sql.append(getLogicalOperator(LogicalOperatorEnum.OR));
                }
                sql.append(column(arrayColumn[i])).append(" LIKE ")
                        .append(getBindingField(value));
            }
            sql.append(SymbolConstant.CLOSE_PARENTHESIS);
        } else {
            sql.append(column(condition)).append(" LIKE ").append(getBindingField(value));
        }
        return sql.toString();
    }

    /**
     * 转成 like 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toSwCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        final String value = condition.getValue() + "%";
        sql.append(column(condition)).append(" LIKE ").append(getBindingField(value));
        return sql.toString();
    }

    /**
     * 转成 like 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toEwCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        final String value = "%" + condition.getValue();
        sql.append(column(condition)).append(" LIKE ").append(getBindingField(value));
        return sql.toString();
    }

    /**
     * 转成 between and 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toBtCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        final BetweenValue betweenValue = (BetweenValue) condition.getValue();
        sql.append(column(condition)).append(" BETWEEN ")
                .append(getBindingField(betweenValue.getStartValue())).append(" AND ")
                .append(getBindingField(betweenValue.getEndValue()));
        return sql.toString();
    }

    /**
     * 转成 <= 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toLteCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append("<=").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    /**
     * 转成 < 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toLtCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append("<").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    /**
     * 转成 >= 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toGteCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append(">=").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    /**
     * 转成 > 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toGtCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append(">").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    /**
     * 转成 = 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toEqCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append("=").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    /**
     * 转成 != 条件
     *
     * @param condition 条件
     * @return SQL条件
     */
    private String toNeqCondition(SingleCondition<Object, Object> condition) {
        final StringBuilder sql = new StringBuilder(32);
        sql.append(column(condition)).append("!=").append(getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String column(SingleCondition<Object, Object> condition) {
        return String.valueOf(condition.getColumn());
        //return PersistentUtil.fieldToColumn(String.valueOf(condition.getColumn()));
    }

    private String column(String column) {
        return column;
        //return PersistentUtil.fieldToColumn(column);
    }

    private String getBindingField(Object value) {
        return ConditionParserUtil.getBindingField(bindName, value, params);
    }

}
