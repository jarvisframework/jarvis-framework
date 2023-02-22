package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.mybatis.provider.BaseEntityExecuteProvider;
import com.jarvis.framework.mybatis.update.CriteriaDelete;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月14日
 */
public interface BaseIdPrimaryKeyEnityMapper<Id extends Serializable, Entity extends BaseIdPrimaryKeyEntity<Id>,
        Column> {

    /**
     * 单个对象插入
     *
     * @param entity
     * @return
     */
    @InsertProvider(value = BaseEntityExecuteProvider.class, method = "insert")
    boolean insert(Entity entity);

    /**
     * 批量对象插入
     *
     * @param entities
     * @return
     */
    @InsertProvider(value = BaseEntityExecuteProvider.class, method = "insertAll")
    int insertAll(Collection<Entity> entities);

    /**
     * 单个对象更新
     *
     * @param entity
     * @return
     */
    @UpdateProvider(value = BaseEntityExecuteProvider.class, method = "update")
    boolean update(Entity entity);

    /**
     * 批量对象更新
     *
     * @param entities
     * @return
     */
    @UpdateProvider(value = BaseEntityExecuteProvider.class, method = "updateAll")
    int updateAll(Collection<Entity> entities);

    /**
     * 单个对象删除
     *
     * @param entity
     * @return
     */
    @DeleteProvider(value = BaseEntityExecuteProvider.class, method = "delete")
    boolean delete(Entity entity);

    /**
     * 批量对象删除
     *
     * @param entities
     * @return
     */
    @DeleteProvider(value = BaseEntityExecuteProvider.class, method = "deleteAll")
    int deleteAll(Collection<Entity> entities);

    /**
     * 按条件更新，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @UpdateProvider(value = BaseEntityExecuteProvider.class, method = "updateBy")
    int updateBy(CriteriaUpdate<Column> criterion);

    /**
     * 根据条件删除，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * critia.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @DeleteProvider(value = BaseEntityExecuteProvider.class, method = "deleteBy")
    int deleteBy(CriteriaDelete<Column> criterion);

    /**
     * 根据条件查询单个对象，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "getBy")
    Entity getBy(CriteriaQuery<Column> criterion);

    /**
     * 根据条件查询单个对象，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "getObjectBy")
    <T> T getObjectBy(@Param("criterion") CriteriaQuery<Column> criterion, @Param("clazz") Class<T> clazz);

    /**
     * 根据条件查询单个对象(多表级联查询)，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "getByMultiple")
    <T> T getByMultiple(@Param("criterion") MultipleQuery criterion, @Param("clazz") Class<T> clazz);

    /**
     * 根据条件查询一组对象，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "queryBy")
    List<Entity> queryBy(CriteriaQuery<Column> criterion);

    /**
     * 单表查询：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion 条件
     * @param clazz 返回对象类型
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "queryObjectsBy")
    <T> List<T> queryObjectsBy(@Param("criterion") CriteriaQuery<Column> criterion, @Param("clazz") Class<T> clazz);

    /**
     * 多表级联查询：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion 条件
     * @param clazz 返回对象类型
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "queryByMultiple")
    <T> List<T> queryByMultiple(@Param("criterion") MultipleQuery criterion, @Param("clazz") Class<T> clazz);

    /**
     * 统计
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "count")
    int count(CriteriaQuery<Column> criterion);

    /**
     * 分页查询
     *
     * @param page
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "page")
    List<Entity> page(@Param("page") Page page, @Param("criterion") CriteriaQuery<Column> criterion);

    /**
     * 根据条件查询是否存在，如果是动态表必须设置表名，设置表名如下：
     *
     * <pre>
     * criterion.tableName("t_ar_wsda_file")
     * </pre>
     *
     * @param criterion
     * @return
     */
    @SelectProvider(value = BaseEntityExecuteProvider.class, method = "exists")
    boolean exists(CriteriaQuery<Column> criterion);

}
