package com.jarvis.framework.core.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface BaseDynamicEntity<Id extends Serializable> extends BaseIdPrimaryKeyEntity<Id>, Map<String, Object> {
    default Collection<String> exclude() {
        return null;
    }

    String tableName();

    void tableName(String var1);
}
