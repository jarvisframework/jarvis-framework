package com.jarvis.framework.mybatis.update.data;

import com.jarvis.framework.util.ColumnFunctionUtil;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月28日
 */
public class ConcatColumn<T> {

    private List<Object> data = new ArrayList<>();

    /**
     * 拼接字段
     *
     * @param columns 一个或多个字段：fondsCode/FondsCode::getCode
     * @return
     */
    public ConcatColumn<T> concatColumn(T... columns) {
        for (final T column : columns) {
            final String col = ColumnFunctionUtil.toDatabaseColumn(column);
            data.add(ColumnValue.newColumn(col));
        }
        return this;
    }

    /**
     * 拼接字符
     *
     * @param values 一个或多个值
     * @return
     */
    public ConcatColumn<T> concatValue(String... values) {
        for (final String value : values) {
            data.add(ColumnValue.newValue(value));
        }
        return this;
    }

    /**
     * 拼接函数
     *
     * @param values 一个或多个值
     * @return
     */
    public ConcatColumn<T> concatFunc(Consumer<FunctionValue<T>> consumer) {
        final FunctionValue<T> func = new FunctionValue<T>();
        consumer.accept(func);
        data.add(func);
        return this;
    }

    /**
     * 拼接 case when 语法
     *
     * @param values 一个或多个值
     * @return
     */
    public ConcatColumn<T> concatCaseWhen(Consumer<CaseWhen<T>> consumer) {
        final CaseWhen<T> cw = CaseWhen.<T>create();
        consumer.accept(cw);
        data.add(cw);
        return this;
    }

    public List<Object> data() {
        Assert.isTrue(data.size() > 0, "请设置拼接值");
        return data;
    }

    /**
     * @return the data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

}
