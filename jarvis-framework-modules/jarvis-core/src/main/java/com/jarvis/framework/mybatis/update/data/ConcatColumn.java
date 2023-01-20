package com.jarvis.framework.mybatis.update.data;

import com.jarvis.framework.util.ColumnFunctionUtil;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class ConcatColumn<T> {

    private List<ColumnValue> data = new ArrayList();

    public ConcatColumn<T> concatColumn(T... columns) {
        T[] var2 = columns;
        int var3 = columns.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            T column = var2[var4];
            String col = ColumnFunctionUtil.toDatabaseColumn(column);
            this.data.add(ColumnValue.newColumn(col));
        }

        return this;
    }

    public ConcatColumn<T> concatValue(String... values) {
        String[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String value = var2[var4];
            this.data.add(ColumnValue.newValue(value));
        }

        return this;
    }

    public List<ColumnValue> data() {
        Assert.isTrue(this.data.size() > 0, "请设置拼接值");
        return this.data;
    }

    public List<ColumnValue> getData() {
        return this.data;
    }

    public void setData(List<ColumnValue> data) {
        this.data = data;
    }
}
