package com.jarvis.framework.core.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public interface BaseDynamicEntity<Id extends Serializable> extends BaseIdPrimaryKeyEntity<Id>, Map<String, Object> {

    /**
     * 不保存的字段集
     *
     * @return Collection
     */
    default Collection<String> exclude() {
        return null;
    }

    /**
     * 表名
     *
     * @return String
     */
    String tableName();

    /**
     * 设置表名
     *
     * @param tableName
     */
    void tableName(String tableName);
}
