package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.function.BuildFunction;
import com.jarvis.framework.mybatis.update.data.CalcColumn;
import com.jarvis.framework.mybatis.update.data.ConcatColumn;
import com.jarvis.framework.mybatis.update.data.FunctionValue;
import com.jarvis.framework.search.ComposedCondition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 更新
 *
 * @author qiucs
 * @version 1.0.0 2021年1月20日
 */
public class CriteriaUpdate<Column>
        extends AbstractCriteriaCondition<Column, CriteriaUpdate<Column>> {

    private final Map<String, Object> data = new HashMap<String, Object>(8);

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 更新字段：name='zhangsan'
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param value 值
     * @return
     */
    public CriteriaUpdate<Column> update(Column column, Object value) {
        this.data.put(toColumn(column), value);
        return this;
    }

    /**
     * 更新数字字段，如：age = age + 1
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param value 值
     * @return
     */
    public CriteriaUpdate<Column> plus(Column column, Number value) {
        final String col = toColumn(column);
        this.data.put(col, CalcColumn.plus(col, value));
        return this;
    }

    /**
     * 更新数字字段，如：num1 = num2 + 10
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param valueColumn 数据库字段或get方法，如：user_name或User::getUserName
     * @param value 值
     * @return
     */
    public CriteriaUpdate<Column> plus(Column column, Column valueColumn, Number value) {
        final String col = toColumn(column);
        final String valCol = toColumn(valueColumn);
        this.data.put(col, CalcColumn.plus(valCol, value));
        return this;
    }

    /**
     * 更新数字字段，如：age = age - 1
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param value 值
     * @return
     */
    public CriteriaUpdate<Column> minus(Column column, Number value) {
        final String col = toColumn(column);
        this.data.put(col, CalcColumn.minus(col, value));
        return this;
    }

    /**
     * 更新数字字段，如：num1 = num2 - 10
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param valueColumn 数据库字段或get方法，如：user_name或User::getUserName
     * @param value 值
     * @return
     */
    public CriteriaUpdate<Column> minus(Column column, Column valueColumn, Number value) {
        final String col = toColumn(column);
        final String valCol = toColumn(valueColumn);
        this.data.put(col, CalcColumn.minus(valCol, value));
        return this;
    }

    /**
     * 更新字段值为字段拼接，如 title = code || '-' || name
     *
     * <pre>
     * concat(Class::getTitle, (c) -> {
     *     c.concatColumn(Class::getCode).concatVaule("-").concatColumn(Class::getName);
     * })
     * </pre>
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param concat
     * @return
     */
    public CriteriaUpdate<Column> concat(Column column, Consumer<ConcatColumn<Column>> concat) {
        final String col = toColumn(column);
        final ConcatColumn<Column> value = new ConcatColumn<>();
        concat.accept(value);
        this.data.put(col, value);
        return this;
    }

    /**
     * 更新字段值为函数，如 title = replace(title, 'a', 'b')
     *
     * <pre>
     * func(Class::getTitle, (f) -> {
     *     f.name("replace").addColumnParam()(Class::getTitle).addColumnParam("a").addColumnParam("b");
     * })
     * </pre>
     *
     * @param column 数据库字段或get方法，如：user_name或User::getUserName
     * @param consumer Consumer
     * @return
     */
    public CriteriaUpdate<Column> func(Column column, Consumer<FunctionValue<Column>> consumer) {
        final String col = toColumn(column);
        final FunctionValue<Column> value = new FunctionValue<>();
        consumer.accept(value);
        this.data.put(col, value);
        return this;
    }

    /**
     * 设置查询表名
     *
     * @param tableName
     * @return
     */
    @Override
    public CriteriaUpdate<Column> tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public CriteriaUpdate<Column> filter(BuildFunction<ComposedCondition<Column>> condition) {
        condition.build(this.filter);
        return this;
    }

    @Override
    public CriteriaUpdate<Column> filter(Consumer<ComposedCondition<Column>> condition) {
        condition.accept(this.filter);
        return this;
    }

}
