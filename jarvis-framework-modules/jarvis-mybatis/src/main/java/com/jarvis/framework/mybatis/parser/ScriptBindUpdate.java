package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ColumnValue;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.util.ConditionParserUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScriptBindUpdate<Column> extends AbstractScriptBind<Column, CriteriaUpdate<Column>, ScriptBindUpdate<Column>> {
    private String columns;

    private ScriptBindUpdate(CriteriaUpdate<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindUpdate<Column> create(CriteriaUpdate<Column> criterion) {
        return new ScriptBindUpdate(criterion);
    }

    public ScriptBindUpdate<Column> parse() {
        this.toSetColumns();
        this.toWhere();
        return this;
    }

    private void toSetColumns() {
        Map<String, Object> data = ((CriteriaUpdate)this.criterion).getData();
        if (null != data && !data.isEmpty()) {
            StringBuilder sql = new StringBuilder(32);
            data.forEach((column, value) -> {
                sql.append(column).append("=");
                if (null == value) {
                    sql.append("null");
                } else if (value instanceof CalcColumn) {
                    sql.append(this.toCalcValue((CalcColumn)value));
                } else if (value instanceof ConcatColumn) {
                    sql.append(this.toConcatColumn((ConcatColumn)value));
                } else {
                    sql.append(ConditionParserUtil.getBindingField("_param", value, this.params));
                }

                sql.append(",");
            });
            sql.setLength(sql.length() - 1);
            this.columns = sql.toString();
        } else {
            throw new FrameworkException("更新字段未设置");
        }
    }

    private String toConcatColumn(ConcatColumn<?> value) {
        return DatabaseIdHolder.isOracle() ? this.oracleConcat(value.data()) : this.concatFunction(value.data());
    }

    private String oracleConcat(List<ColumnValue> data) {
        StringBuilder sql = new StringBuilder();

        for(Iterator var3 = data.iterator(); var3.hasNext(); sql.append("||")) {
            ColumnValue cv = (ColumnValue)var3.next();
            if (cv.isValue()) {
                sql.append("'").append(cv.getValue()).append("'");
            } else {
                sql.append(cv.getValue());
            }
        }

        sql.setLength(sql.length() - 2);
        return sql.toString();
    }

    private String concatFunction(List<ColumnValue> data) {
        StringBuilder sql = new StringBuilder("concat(");

        for(Iterator var3 = data.iterator(); var3.hasNext(); sql.append(",")) {
            ColumnValue cv = (ColumnValue)var3.next();
            if (cv.isValue()) {
                sql.append("'").append(cv.getValue()).append("'");
            } else {
                sql.append(cv.getValue());
            }
        }

        sql.setLength(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }

    private String toCalcValue(CalcColumn calc) {
        return 1 == calc.getType() ? calc.getColumn() + "+" + calc.getValue() : calc.getColumn() + "-" + calc.getValue();
    }

    public String getColumns() {
        return this.columns;
    }
}
