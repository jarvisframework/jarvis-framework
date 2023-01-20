package com.jarvis.platform.app.enums;

import com.jarvis.framework.core.code.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum DataTypeEnum implements CodeEnum<String> {

    VARCHAR("varchar", "字符型"),
    TEXT("text", "文本型"),
    INT("int", "整数型"),
    BIGINT("bigint", "长整型"),
    DECIMAL("decimal", "小数型"),
    DATE("date", "日期型"),
    DATETIME("datetime", "日期型");

    private final String value;
    private final String text;
}
