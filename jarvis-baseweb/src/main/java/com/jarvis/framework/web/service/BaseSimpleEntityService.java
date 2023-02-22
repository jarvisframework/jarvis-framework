package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.mapper.BaseSimpleEntityMapper;
import com.jarvis.framework.mybatis.update.CriteriaDelete;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月26日
 */
public interface BaseSimpleEntityService<Id extends Serializable, Entity extends BaseSimpleEntity<Id>,
                                         Mapper extends BaseSimpleEntityMapper<Id, Entity>> {

    /**
     * 新增
     *
     * @param entity 对象
     * @return boolean
     */
    boolean insert(Entity entity);

    /**
     * 批量新增
     *
     * @param entities 对象集合
     * @return 新增成功数量
     */
    int insertAll(Collection<Entity> entities);

    /**
     * 更新
     *
     * @param entity 对象
     * @return boolean
     */
    boolean update(Entity entity);

    /**
     * 批量修改
     *
     * @param entities 对象集合
     * @return 数量
     */
    int updateAll(Collection<Entity> entities);

    /**
     * 删除
     *
     * @param entity 对象
     * @return boolean
     */
    boolean delete(Entity entity);

    /**
     * 批量删除
     *
     * @param entities 对象集合
     * @return 数量
     */
    int deleteAll(Collection<Entity> entities);

    /**
     * 删除
     *
     * @param id id值
     * @return boolean
     */
    boolean deleteById(Id id);

    /**
     * 批量按id删除
     *
     * @param ids id值集合
     * @return int
     */
    int deleteByIds(Collection<Id> ids);

    /**
     * 批量操作
     *
     * @param data 数据集
     * @param fn 操作
     * @param batchSize 批量
     * @return int
     */
    <T> int batch(Collection<T> data, BiFunction<T, Mapper, Integer> fn, int batchSize);

    /**
     * 批量操作（batch size 默认为512）
     *
     * @param data 数据集
     * @param fn 操作
     * @return int
     */
    <T> int batch(Collection<T> data, BiFunction<T, Mapper, Integer> fn);

    /**
     * 根据id查询
     *
     * @param id ID值
     * @return 对象
     */
    Entity getById(Id id);

    /**
     * 统计
     *
     * @param criterion 条件
     * @return 总数
     */
    int count(CriteriaQuery<Getter<Entity>> criterion);

    /**
     * 分页
     *
     * @param page 分页信息
     * @param criterion 条件
     * @return 集合
     */
    List<?> page(Page page, CriteriaQuery<Getter<Entity>> criterion);

    /**
     * 根据条件更新
     *
     * <pre>
     * CriteriaUpdate<Getter<Entity>> criterion = createCriteriaUpdate();
     * criterion.update(Entity::getXaa, 2021).update(Entity::getXab, "SH001")
     *     .filter(condition -> {
     *         // 设置过滤条件
     *
     *         return condition;
     *     });
     * </pre>
     *
     * @param criterion 条件
     * @return 更新数量
     */
    int updateBy(CriteriaUpdate<Getter<Entity>> criterion);

    /**
     * 根据条件删除
     *
     * <pre>
     * CriteriaDelete<Getter<Entity>> criterion = createCriteriaDelete();
     * criterion.filter(condition -> {
     *     // 设置过滤条件
     *
     *     return condition;
     * });
     * </pre>
     *
     * @param criterion 条件
     * @return 删除数量
     */
    int deleteBy(CriteriaDelete<Getter<Entity>> criterion);

    /**
     * 根据条件检索
     *
     * @param criterion 条件
     * @return 集合
     */
    Entity getBy(CriteriaQuery<Getter<Entity>> criterion);

    /**
     * 根据条件检索
     *
     * @param criterion 条件
     * @return 集合
     */
    List<Entity> queryBy(CriteriaQuery<Getter<Entity>> criterion);

    /**
     * 判断是否存在
     *
     * @param criterion 条件
     * @return boolean
     */
    boolean exists(CriteriaQuery<Getter<Entity>> criterion);

}
