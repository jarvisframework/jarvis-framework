package com.jarvis.framework.database.upgrade.constant;

import com.jarvis.framework.core.code.CodeEnum;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月1日
 */
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

    private DataTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     *
     * @see com.jarvis.framework.core.code.CodeEnum#value()
     */
    @Override
    public String value() {
        return value;
    }

    /**
     *
     * @see com.jarvis.framework.core.code.CodeEnum#text()
     */
    @Override
    public String text() {
        return text;
    }

}
