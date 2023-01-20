package com.jarvis.framework.search;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

import java.io.Serializable;

public class CriteriaQueryBuilder {

    public static <T extends Serializable> EntityQuery<T> createEntityQueryCriterion() {
        return EntityQuery.create();
    }

    public static <Entity extends BaseSimpleEntity<?>> EntityQuery<Entity> createEntityCriterion() {
        return EntityQuery.create();
    }

    public static CriteriaQuery<String> createDynamicCriterion() {
        return DynamicEntityQuery.create();
    }

    public static DynamicEntityQuery createDynamicEntityCriterion() {
        return DynamicEntityQuery.create();
    }
}
