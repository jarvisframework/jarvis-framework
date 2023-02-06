package com.jarvis.framework.mybatis.update.data;

import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月12日
 */
public class FunctionValue<T> {

    /** 函数名称 */
    private String name;

    /** 参数列表 */
    private List<ColumnValue> params = new ArrayList<>();

    /** 别名 */
    private String alias;

    public static <T> FunctionValue<T> create(String name) {
        return new FunctionValue<T>().name(name);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the params
     */
    public List<ColumnValue> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(List<ColumnValue> params) {
        this.params = params;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public FunctionValue<T> name(String name) {
        this.name = name;
        return this;
    }

    public FunctionValue<T> alias(String alias) {
        this.alias = alias;
        return this;
    }

    public FunctionValue<T> addColumnParam(T column) {
        this.params.add(ColumnValue.newColumn(ColumnFunctionUtil.toDatabaseColumn(column)));
        return this;
    }

    public FunctionValue<T> addValueParam(Object value) {
        this.params.add(ColumnValue.newValue(value));
        return this;
    }
}
