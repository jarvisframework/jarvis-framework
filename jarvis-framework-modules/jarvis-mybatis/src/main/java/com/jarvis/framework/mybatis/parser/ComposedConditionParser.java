package com.jarvis.framework.mybatis.parser;

import java.util.List;

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

    public String parse(ComposedCondition<?> composedCondition) {
        if (null == composedCondition) {
            return "";
        } else {
            StringBuilder sql = new StringBuilder(64);
            List<ConditionExpression> conditionExpressions = composedCondition.getConditionExpressions();
            int i = 0;

            for(int len = conditionExpressions.size(); i < len; ++i) {
                ConditionExpression conditionExpression = (ConditionExpression)conditionExpressions.get(i);
                if (null != conditionExpression) {
                    if (i > 0) {
                        sql.append(this.getLogicalOperator(composedCondition.getOperator()));
                    }

                    if (conditionExpression.isSingleCondition()) {
                        SingleCondition<Object, Object> c = (SingleCondition)conditionExpression;
                        sql.append(this.parseSingleCondition(c));
                    } else {
                        ComposedCondition<?> c = (ComposedCondition)conditionExpression;
                        sql.append("(").append(this.parse(c)).append(")");
                    }
                }
            }

            return sql.toString();
        }
    }

    private String getLogicalOperator(LogicalOperatorEnum op) {
        return " " + op.name().toUpperCase() + " ";
    }

    private String parseSingleCondition(SingleCondition<Object, Object> condition) {
        ConditionOperatorEnum operator = condition.getOperator();
        switch(operator) {
        case EQ:
            return this.toEqCondition(condition);
        case NEQ:
            return this.toNeqCondition(condition);
        case GT:
            return this.toGtCondition(condition);
        case GTE:
            return this.toGteCondition(condition);
        case LT:
            return this.toLtCondition(condition);
        case LTE:
            return this.toLteCondition(condition);
        case BT:
            return this.toBtCondition(condition);
        case LIKE:
            return this.toLikeCondition(condition);
        case SW:
            return this.toSwCondition(condition);
        case EW:
            return this.toEwCondition(condition);
        case IN:
            return this.toInCondition(condition);
        case NIN:
            return this.toNinCondition(condition);
        case NULL:
            return this.toNullCondition(condition);
        case NNULL:
            return this.toNnullCondition(condition);
        default:
            throw new FrameworkException("不支持操作符[" + operator.getName() + "]");
        }
    }

    private String toNnullCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(" IS NOT NULL ");
        return sql.toString();
    }

    private String toNullCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(" IS NULL ");
        return sql.toString();
    }

    private String toNinCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(" NOT IN \n").append(this.getBindingField(condition.getValue()));
        sql.append("<foreach collection=\"").append(this.getBindingField(condition.getValue())).append("\" item=\"val\" separator=\",\" open=\"(\" close=\")\"> \\n");
        sql.append(PersistentUtil.getBindingField("val"));
        sql.append("</foreach>\n");
        return sql.toString();
    }

    private String toInCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(" IN (");
        Object[] values = (Object[])((Object[])condition.getValue());
        int i = 0;

        for(int len = values.length; i < len; ++i) {
            if (i > 0) {
                sql.append(",");
            }

            sql.append(this.getBindingField(values[i]));
        }

        sql.append(")");
        return sql.toString();
    }

    private String toLikeCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        Object column = condition.getColumn();
        String value = "%" + condition.getValue() + "%";
        if (column instanceof String[]) {
            String[] arrayColumn = (String[])((String[])column);
            sql.append("(");
            int i = 0;

            for(int len = arrayColumn.length; i < len; ++i) {
                if (i > 0) {
                    sql.append(this.getLogicalOperator(LogicalOperatorEnum.OR));
                }

                sql.append(this.column(arrayColumn[i])).append(" LIKE ").append(this.getBindingField(value));
            }

            sql.append(")");
        } else {
            sql.append(this.column(condition)).append(" LIKE ").append(this.getBindingField(value));
        }

        return sql.toString();
    }

    private String toSwCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        String value = condition.getValue() + "%";
        sql.append(this.column(condition)).append(" LIKE ").append(this.getBindingField(value));
        return sql.toString();
    }

    private String toEwCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        String value = "%" + condition.getValue();
        sql.append(this.column(condition)).append(" LIKE ").append(this.getBindingField(value));
        return sql.toString();
    }

    private String toBtCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        BetweenValue betweenValue = (BetweenValue)condition.getValue();
        sql.append(this.column(condition)).append(" BETWEEN ").append(this.getBindingField(betweenValue.getStartValue())).append(" AND ").append(this.getBindingField(betweenValue.getEndValue()));
        return sql.toString();
    }

    private String toLteCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append("<=").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String toLtCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append("<").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String toGteCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(">=").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String toGtCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append(">").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String toEqCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append("=").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String toNeqCondition(SingleCondition<Object, Object> condition) {
        StringBuilder sql = new StringBuilder(32);
        sql.append(this.column(condition)).append("!=").append(this.getBindingField(condition.getValue()));
        return sql.toString();
    }

    private String column(SingleCondition<Object, Object> condition) {
        return String.valueOf(condition.getColumn());
    }

    private String column(String column) {
        return column;
    }

    private String getBindingField(Object value) {
        return ConditionParserUtil.getBindingField(this.bindName, value, this.params);
    }
}
