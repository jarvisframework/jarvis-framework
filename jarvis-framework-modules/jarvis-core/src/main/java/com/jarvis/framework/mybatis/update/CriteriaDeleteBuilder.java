package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

public class CriteriaDeleteBuilder {


    public static <T extends BaseSimpleEntity<?>> EntityDelete<T> createEntityCriterion() {
        return EntityDelete.create();
    }

    public static DynamicEntityDelete createDynamicCriterion() {
        return DynamicEntityDelete.create();
    }
}
