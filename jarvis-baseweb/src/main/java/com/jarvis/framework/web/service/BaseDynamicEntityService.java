package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.mybatis.mapper.BaseDynamicEntityMapper;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.mybatis.update.DynamicEntityDelete;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月27日
 */
public interface BaseDynamicEntityService<Id extends Serializable, Entity extends BaseDynamicEntity<Id>,
                                          Mapper extends BaseDynamicEntityMapper<Id, Entity>> {

    /**
     * 新增
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean insert(Entity entity);

    /**
     * 批量新增
     *
     * @param entities 实体对象集合
     * @return 新增成功数量
     */
    int insertAll(Collection<Entity> entities);

    /**
     * 更新
     *
     * @param entity 实体数量
     * @return boolean
     */
    boolean update(Entity entity);

    /**
     * 批量修改
     *
     * @param entities 实体集合
     * @return 更新成功数量
     */
    int updateAll(Collection<Entity> entities);

    /**
     * 删除
     *
     * @param entity 实体对象
     * @return boolean
     */
    boolean delete(Entity entity);

    /**
     * 批量删除
     *
     * @param entities 实体集合
     * @return 删除成功数量
     */
    int deleteAll(Collection<Entity> entities);

    /**
     * 删除
     *
     * @param tableId 表id
     * @param id 数据ID
     * @return boolean
     */
    boolean deleteById(Id tableId, Id id);

    /**
     * 删除
     *
     * @param tableId 表id
     * @param ids 数据ID集合
     * @return int
     */
    int deleteByIds(Id tableId, Collection<Id> ids);

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
     * @param tableId 表id
     * @param id 数据ID
     * @return 实体对象
     */
    Entity getById(Id tableId, Id id);

    /**
     * 统计
     *
     * @param criterion 条件
     * @return 数量
     */
    int count(DynamicEntityQuery criterion);

    /**
     * 分页
     *
     * @param page 分页信息
     * @param criterion 条件
     * @return 对象集合
     */
    List<?> page(Page page, DynamicEntityQuery criterion);

    /**
     * 根据条件更新
     *
     * <pre>
     * CriteriaUpdate<String> criterion = createCriteriaUpdate();
     * criterion.tableName("t_ar_wsda_file")
     *     .update("yearCode", 2021).update("fondsCode", "SH001")
     *     .filter(condition -> {
     *         // 设置过滤条件
     *
     *         return condition;
     *     });
     * </pre>
     *
     * @param criterion 条件
     * @return 更新成功数量
     */
    int updateBy(CriteriaUpdate<String> criterion);

    /**
     * 根据条件删除
     *
     * <pre>
     * DynamicEntityDelete criterion = createCriteriaDelete();
     * criterion.tableName("t_ar_wsda_file").filter(condition -> {
     *     // 设置过滤条件
     *
     *     return condition;
     * });
     * </pre>
     *
     * @param criterion 条件
     * @return 删除成功数量
     */
    int deleteBy(DynamicEntityDelete criterion);

    /**
     * 根据条件检索
     *
     * @param criterion 条件
     * @return 实体对象
     */
    Entity getBy(DynamicEntityQuery criterion);

    /**
     * 根据条件检索
     *
     * @param criterion 条件
     * @return 实体集合
     */
    List<Entity> queryBy(DynamicEntityQuery criterion);

    /**
     * 判断是否存在
     *
     * @param criterion 条件
     * @return boolean
     */
    boolean exists(DynamicEntityQuery criterion);

    /**
     * 根据tableId获取表名
     *
     * @param tableId 表id
     * @return String
     */
    String getTableName(Id tableId);
}
