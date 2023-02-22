package com.jarvis.framework.mybatis.handler.support;


import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月6日
 */
public interface EntityFillingSupportHandler {

    boolean support(BaseIdPrimaryKeyEntity<?> entity);

    /**
     * 插入时，自动填充数据
     *
     * @param entity
     */
    void insert(BaseIdPrimaryKeyEntity<?> entity);

    /**
     * 更新时，自动填充数据
     *
     * @param entity
     */
    void update(BaseIdPrimaryKeyEntity<?> entity);
}
