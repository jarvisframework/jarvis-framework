package com.jarvis.framework.database.upgrade.constant;


import com.jarvis.framework.core.code.CodeEnum;

public enum DataTypeEnum implements CodeEnum<String> {
    VARCHAR("varchar", "字符型"),
    TEXT("text", "文本型"),
    INT("int", "整数型"),
    BIGINT("bigint", "长整型"),
    DECIMAL("decimal", "小数型"),
    DATE("date", "日期型"),
    DATETIME("datetime", "日期时间型");

    private String value;
    private String text;

    DataTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String value() {
        return this.value;
    }

    public String text() {
        return this.text;
    }
}
