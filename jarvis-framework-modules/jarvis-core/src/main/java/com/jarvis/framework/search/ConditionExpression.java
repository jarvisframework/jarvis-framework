package com.jarvis.framework.search;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.io.Serializable;

@JsonTypeInfo(
    use = Id.NAME,
    include = As.PROPERTY
)
@JsonSubTypes({@Type(
    value = ComposedCondition.class,
    name = "Composed"
), @Type(
    value = SingleCondition.class,
    name = "Single"
)})
public interface ConditionExpression extends Serializable {
    boolean isSingleCondition();
}
