package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * 条件表达式
 *
 * @author qiucs
 * @version 1.0.0 2021年1月13日
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ComposedCondition.class, name = "Composed"),
        @JsonSubTypes.Type(value = SingleCondition.class, name = "Single")
})
public interface ConditionExpression extends Serializable {

    /**
     * 标记条件类型：true（一个条件） false（一组条件）
     *
     * @return boolean
     */
    boolean isSingleCondition();

}
