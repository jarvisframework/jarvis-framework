package com.jarvis.framework.function;

import java.io.Serializable;

@FunctionalInterface
public interface Getter<Entity extends Serializable> extends SerializableFunction<Entity, Object> {
}
