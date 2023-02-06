package com.jarvis.framework.search;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月26日
 */
public class CriteriaQueryBuilder {

    /**
     * 构造动态表检索条件器
     *
     * @return
     */
    public static <T extends Serializable> EntityQuery<T> createEntityQueryCriterion() {
        return EntityQuery.<T>create();
    }

    /**
     * 构造实体表检索条件器
     *
     * @param <Entity>
     * @return
     */
    public static <Entity extends BaseSimpleEntity<?>> EntityQuery<Entity> createEntityCriterion() {
        return EntityQuery.create();
    }

    /**
     * 构造动态表检索条件器
     *
     * @return
     */
    public static CriteriaQuery<String> createDynamicCriterion() {
        return DynamicEntityQuery.create();
    }

    /**
     * 构造动态表检索条件器
     *
     * @return
     */
    public static DynamicEntityQuery createDynamicEntityCriterion() {
        return DynamicEntityQuery.create();
    }

}
