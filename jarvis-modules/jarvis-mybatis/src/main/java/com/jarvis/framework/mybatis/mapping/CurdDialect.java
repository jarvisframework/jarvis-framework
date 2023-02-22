package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;

import java.util.Collection;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月18日
 */
public interface CurdDialect {

    /**
     * 批量插入
     *
     * @param entities 对象集合
     * @return SQL语句
     */
    String insertAll(Collection<BaseIdPrimaryKeyEntity<?>> entities);

    /**
     * 批量更新
     *
     * @param entities 对象集合
     * @return SQL语句
     */
    String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> entities);

    /**
     * 批量删除
     *
     * @param entities 对象集合
     * @return SQL语句
     */
    String deleteAll(Collection<BaseIdPrimaryKeyEntity<?>> entities);

    /**
     * 根据ID删除
     *
     * @param tableName 表名
     * @return SQL语句
     */
    String deleteById(String tableName);

    /**
     * 根据ID删除
     *
     * @param tableName 表名
     * @return SQL语句
     */
    String deleteByIds(String tableName);

    /**
     * 根据ID检索
     *
     * @param tableName 表名
     * @return SQL语句
     */
    String getById(String tableName);

    /**
     * 根据条件查询单个对象
     *
     * @param criterion 条件
     * @return SQL语句
     */
    String getBy(CriteriaQuery<?> criterion);

    /**
     * 根据条件查询单个对象(多表级联查询)
     *
     * @param criterion 条件
     * @return SQL语句
     */
    String getBy(MultipleQuery criterion);

    /**
     * 分页
     *
     * @param criterion 条件
     * @return SQL语句
     */
    String page(Page page, CriteriaQuery<?> criterion);

    /**
     * 根据条件查询判断是否存在
     *
     * @param criterion 条件
     * @return SQL语句
     */
    String exists(CriteriaQuery<?> criterion);
}
