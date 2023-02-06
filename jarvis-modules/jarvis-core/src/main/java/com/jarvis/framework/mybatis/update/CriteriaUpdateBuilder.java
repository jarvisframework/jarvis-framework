package com.jarvis.framework.mybatis.update;

import com.jarvis.framework.core.entity.BaseSimpleEntity;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月26日
 */
public class CriteriaUpdateBuilder {

    /**
     * 构造实体表更新条件器
     *
     * @param <Entity>
     * @return
     */
    public static <T extends BaseSimpleEntity<?>> EntityUpdate<T> createEntityCriterion() {
        return EntityUpdate.<T>create();
    }

    /**
     * 构造动态表更新条件器
     *
     * @return
     */
    public static DynamicEntityUpdate createDynamicCriterion() {
        return DynamicEntityUpdate.create();
    }

}
