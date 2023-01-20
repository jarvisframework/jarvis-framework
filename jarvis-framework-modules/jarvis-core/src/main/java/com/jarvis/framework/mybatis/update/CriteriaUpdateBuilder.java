package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

public class CriteriaUpdateBuilder {

    public static <T extends BaseSimpleEntity<?>> EntityUpdate<T> createEntityCriterion() {
        return EntityUpdate.create();
    }

    public static DynamicEntityUpdate createDynamicCriterion() {
        return DynamicEntityUpdate.create();
    }
}
