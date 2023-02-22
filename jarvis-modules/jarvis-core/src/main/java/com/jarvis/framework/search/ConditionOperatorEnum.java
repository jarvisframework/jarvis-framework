package com.jarvis.framework.search;

/**
 * 条件关系 等于、大于、小于等等
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月13日
 */
public enum ConditionOperatorEnum {

    EQ("等于", "="),

    NEQ("不等于", "!="),

    BT("介于", "between"),

    LT("小于", "<"),

    LTE("小于等于", "<="),

    GT("大于", ">"),

    GTE("大于等于", ">="),

    NULL("为空", "is null"),

    NNULL("不为空", "not null"),

    LIKE("包含", "like"),

    SW("左包含", "like"),

    EW("右包含", "like"),

    IN("属于", "in"),

    NIN("不属于", "not in");

    private String name;

    private String code;

    private ConditionOperatorEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
