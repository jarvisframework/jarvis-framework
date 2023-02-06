package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.mapping.DatabaseIdHolder;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.CaseWhen;
import com.jarvis.framework.mybatis.update.data.ColumnValue;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.mybatis.update.util.DatabaseFunctionUtil;
import com.jarvis.framework.mybatis.util.ConditionParserUtil;

import java.util.List;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月15日
 */
public class ScriptBindUpdate<Column>
        extends AbstractScriptBind<Column, CriteriaUpdate<Column>, ScriptBindUpdate<Column>> {

    private String columns;

    private ScriptBindUpdate(CriteriaUpdate<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindUpdate<Column> create(CriteriaUpdate<Column> criterion) {
        return new ScriptBindUpdate<Column>(criterion);
    }

    @Override
    public ScriptBindUpdate<Column> parse() {
        //
        toSetColumns();

        toWhere();

        return this;
    }

    private void toSetColumns() {
        final Map<String, Object> data = criterion.getData();
        if (null == data || data.isEmpty()) {
            throw new FrameworkException("更新字段未设置");
        }
        final StringBuilder sql = new StringBuilder(32);
        data.forEach((column, value) -> {
            sql.append(column).append("=");
            if (null == value) {
                sql.append("null");
            } else if (value instanceof CalcColumn) {
                sql.append(toCalcValue((CalcColumn) value));
            } else if (value instanceof ConcatColumn) {
                sql.append(toConcatColumn((ConcatColumn<?>) value));
            } else if (value instanceof FunctionValue) {
                sql.append(toFunctionValue((FunctionValue<?>) value));
            } else {
                sql.append(ConditionParserUtil.getBindingField(bindName, value, params));
            }
            sql.append(SymbolConstant.COMMA);
        });
        sql.setLength(sql.length() - 1);
        this.columns = sql.toString();
    }

    private String toConcatColumn(ConcatColumn<?> value) {
        if (DatabaseIdHolder.isOracle()) {
            return oracleConcat(value.data());
        }
        return concatFunction(value.data());
    }

    private String oracleConcat(List<Object> data) {
        return toConcatSql(data, " || ");
    }

    private String concatFunction(List<Object> data) {
        final StringBuilder sql = new StringBuilder("concat");
        sql.append(toConcatSql(data, ", "));
        return sql.toString();
    }

    private String toConcatSql(List<Object> data, String delim) {
        final StringBuilder sql = new StringBuilder(SymbolConstant.OPEN_PARENTHESIS);
        for (final Object c : data) {

            if (c instanceof ColumnValue) {
                final ColumnValue cv = (ColumnValue) c;
                if (cv.isValue()) {
                    sql.append(DatabaseFunctionUtil.toValue(cv.getValue()));
                } else {
                    sql.append(cv.getValue());
                }
            } else if (c instanceof FunctionValue) {
                final FunctionValue<?> func = (FunctionValue<?>) c;
                sql.append(toFunctionValue(func));
            } else if (c instanceof CaseWhen) {
                final CaseWhen<?> cw = (CaseWhen<?>) c;
                sql.append(DatabaseFunctionUtil.toCaseWhen(cw, false));
            } else {
                throw BusinessException.create("UPDATE语句不支持[%s]类", c.getClass().getName());
            }
            sql.append(delim);
        }

        final int len = delim.length();
        final int end = sql.length();
        sql.replace(end - len, end, SymbolConstant.CLOSE_PARENTHESIS);

        return sql.toString();
    }

    private String toCalcValue(CalcColumn calc) {
        if (1 == calc.getType()) {
            return calc.getColumn() + "+" + calc.getValue();
        }
        return calc.getColumn() + "-" + calc.getValue();
    }

    private String toFunctionValue(FunctionValue<?> fv) {
        return DatabaseFunctionUtil.toFunctionValue(fv, false);
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

}
