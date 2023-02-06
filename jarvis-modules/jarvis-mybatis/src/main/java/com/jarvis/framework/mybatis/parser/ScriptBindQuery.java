package com.jarvis.framework.mybatis.parser;

import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.mybatis.update.util.DatabaseFunctionUtil;
import com.jarvis.framework.search.CriteriaQuery;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月14日
 */
public class ScriptBindQuery<Column>
        extends AbstractScriptBind<Column, CriteriaQuery<Column>, ScriptBindQuery<Column>> {

    private String columns;

    private String orderBy;

    private String groupBy;

    ScriptBindQuery(CriteriaQuery<Column> criterion) {
        this.criterion = criterion;
    }

    public static <Column> ScriptBindQuery<Column> create(CriteriaQuery<Column> criterion) {
        return new ScriptBindQuery<Column>(criterion);
    }

    @Override
    public ScriptBindQuery<Column> parse() {
        //
        toSelectColumns();

        toWhere();

        toGroupBy();

        toOrderBy();

        return this;
    }

    protected void toSelectColumns() {

        columns = StringUtils.collectionToCommaDelimitedString(
                criterion.getColumns().stream().map(col -> {
                    if (col instanceof FunctionValue) {
                        return DatabaseFunctionUtil.toFunctionValue((FunctionValue<?>) col, true);
                    }
                    return String.valueOf(col);
                }).collect(Collectors.toList()));
        if (!StringUtils.hasLength(columns)) {
            this.columns = "*";
        }
    }

    protected void toGroupBy() {

        groupBy = StringUtils.collectionToCommaDelimitedString(
                //criterion.getGroupBy().stream().map(str -> PersistentUtil.fieldToColumn(str)).collect(Collectors.toList())
                criterion.getGroupBy());
    }

    protected void toOrderBy() {
        /*final StringBuilder orderBy = new StringBuilder(32);
        criterion.getOrders().forEach(order -> {
            orderBy.append(PersistentUtil.fieldToColumn(order.getColumn())).append(SymbolConstant.SPACE)
                .append(order.getOrderBy().name().toUpperCase()).append(SymbolConstant.COMMA);
        });
        if (orderBy.length() > 0) {
            orderBy.setLength(orderBy.length() - 1);
            this.orderBy = orderBy.toString();
        }*/
        this.orderBy = StringUtils.collectionToCommaDelimitedString(
                criterion.getOrders().stream().map(o -> {
                    return o.getColumn() + SymbolConstant.SPACE + o.getOrderBy().name().toUpperCase();
                }).collect(Collectors.toList()));
    }

    /**
     * @return the columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * @return the groupBy
     */
    public String getGroupBy() {
        return groupBy;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy() {
        return orderBy;
    }

}
