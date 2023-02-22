package com.jarvis.framework.core.entity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月6日
 */
public interface SortSimpleEntity {

    /**
     * 获取排序号
     *
     * @return
     */
    Integer getSortOrder();

    /**
     * 设置排序号
     *
     * @param sortOrder
     */
    void setSortOrder(Integer sortOrder);
}
