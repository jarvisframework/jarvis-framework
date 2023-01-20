package com.jarvis.framework.mybatis.update.data;

import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.ArrayList;
import java.util.List;

public class FunctionValue<T> {

    private String name;

    private List<ColumnValue> params = new ArrayList();

    private String alias;

    public static <T> FunctionValue<T> create(String name) {
        return (new FunctionValue()).name(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnValue> getParams() {
        return this.params;
    }

    public void setParams(List<ColumnValue> params) {
        this.params = params;
    }

    public String getAlias() {
        return this.alias;
    }

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

    public FunctionValue<T> addValueParam(String value) {
        this.params.add(ColumnValue.newValue(value));
        return this;
    }
}
