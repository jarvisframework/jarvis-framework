package com.jarvis.framework.mybatis.update.data;

import com.jarvis.framework.util.ColumnFunctionUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * case c1 when v1 then a when v2 then b else c end
 *
 * @author qiucs
 * @version 1.0.0 2022年12月2日
 */
public class CaseWhen<T> {

    private String column;

    private Map<Object, Object> whenMap = new LinkedHashMap<>();

    private ColumnValue other;

    private String alias;

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the whenMap
     */
    public Map<Object, Object> getWhenMap() {
        return whenMap;
    }

    /**
     * @param whenMap the whenMap to set
     */
    public void setWhenMap(Map<Object, Object> whenMap) {
        this.whenMap = whenMap;
    }

    /**
     * @return the other
     */
    public ColumnValue getOther() {
        return other;
    }

    /**
     * @param other the other to set
     */
    public void setOther(ColumnValue other) {
        this.other = other;
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

    /**
     * 创建对象
     *
     * @param <T> String/Getter
     * @return CaseWhen
     */
    public static <T> CaseWhen<T> create() {
        return new CaseWhen<T>();
    }

    /**
     * 字段
     *
     * @param column 字段
     * @return CaseWhen
     */
    public CaseWhen<T> column(T column) {
        this.column = ColumnFunctionUtil.toDatabaseColumn(column);
        return this;
    }

    /**
     * 设置 when val then toVal
     *
     * @param val 原值
     * @param toVal 转换后的值
     * @return CaseWhen
     */
    public CaseWhen<T> when(Object val, Object toVal) {
        this.whenMap.put(val, toVal);
        return this;
    }

    /**
     * 设置else值
     *
     * @param value 其他值
     * @return CaseWhen
     */
    public CaseWhen<T> otherValue(Object value) {
        this.other = ColumnValue.newValue(value);
        return this;
    }

    /**
     * 设置else值
     *
     * @param column 字段
     * @return CaseWhen
     */
    public CaseWhen<T> otherColumn(T column) {
        this.other = ColumnValue.newColumn(ColumnFunctionUtil.toDatabaseColumn(column));
        return this;
    }
}
