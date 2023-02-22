package com.jarvis.framework.mybatis.handler;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月25日
 */
public interface EntityAutoFillingHandler {

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
