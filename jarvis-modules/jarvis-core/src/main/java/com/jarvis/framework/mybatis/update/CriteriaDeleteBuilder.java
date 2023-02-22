package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月26日
 */
public class CriteriaDeleteBuilder {

    /**
     * 构造实体表删除条件器
     *
     * @param <Entity>
     * @return
     */
    public static <T extends BaseSimpleEntity<?>> EntityDelete<T> createEntityCriterion() {
        return EntityDelete.<T>create();
    }

    /**
     * 构造动态表删除条件器
     *
     * @return
     */
    public static DynamicEntityDelete createDynamicCriterion() {
        return DynamicEntityDelete.create();
    }
}
