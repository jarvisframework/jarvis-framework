package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.mybatis.mapper.BaseDynamicEntityMapper;
import com.jarvis.framework.mybatis.update.CriteriaDeleteBuilder;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.mybatis.update.CriteriaUpdateBuilder;
import com.jarvis.framework.mybatis.update.DynamicEntityDelete;
import com.jarvis.framework.mybatis.update.DynamicEntityUpdate;
import com.jarvis.framework.search.CriteriaQueryBuilder;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.framework.web.service.BaseDynamicEntityService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月27日
 */
public abstract class BaseDynamicEntityServiceImpl<Id extends Serializable, Entity extends BaseDynamicEntity<Id>,
                                                   Mapper extends BaseDynamicEntityMapper<Id, Entity>>
    implements BaseDynamicEntityService<Id, Entity, Mapper> {

    private final Class<Mapper> mapperClass = mapperClass();

    @Autowired
    protected Mapper baseMapper;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    protected Mapper getBaseMapper() {
        return this.baseMapper;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Class<Mapper> mapperClass() {
        final Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(getClass());
        final Type value = typeVariableMap.entrySet().stream().filter(e -> e.getKey().getName().equals("Mapper"))
            .findFirst().get()
            .getValue();
        return (Class<Mapper>) value;
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#insert(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(Entity entity) {
        return getBaseMapper().insert(entity);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#insertAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> {
            return insert(e) ? 1 : 0;
        });
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#update(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(Entity entity) {
        return getBaseMapper().update(entity);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#updateAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> {
            return update(e) ? 1 : 0;
        });
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#delete(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Entity entity) {
        return getBaseMapper().delete(entity);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> {
            return delete(e) ? 1 : 0;
        });
    }

    /**
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#deleteById(java.io.Serializable, java.io.Serializable)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Id tableId, Id id) {
        return getBaseMapper().deleteById(getTableName(tableId), id);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#deleteByIds(java.io.Serializable, java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByIds(Id tableId, Collection<Id> ids) {
        final String tableName = getTableName(tableId);
        return batch(ids, (id, m) -> {
            return m.deleteById(tableName, id) ? 1 : 0;
        });
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#batch(java.util.Collection, java.util.function.BiFunction, int)
     */
    @Override
    public <T> int batch(Collection<T> data, BiFunction<T, Mapper, Integer> fn, int batchSize) {
        int count = 0;
        final SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            final Mapper mapper = session.getMapper(mapperClass);
            final int size = data.size();
            int i = 1;
            for (final T element : data) {
                count += fn.apply(element, mapper);
                if ((i % batchSize == 0) || i == size) {
                    session.flushStatements();
                }
                i++;
            }
            session.commit(!TransactionSynchronizationManager.isSynchronizationActive());
        } catch (final Exception e) {
            session.rollback();
            throw new BusinessException(e);
        } finally {
            session.close();
        }
        return count;
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#batch(java.util.Collection, java.util.function.BiFunction)
     */
    @Override
    public <T> int batch(Collection<T> data, BiFunction<T, Mapper, Integer> fn) {
        return batch(data, fn, 512);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#getById(java.io.Serializable, java.io.Serializable)
     */
    @Override
    public Entity getById(Id tableId, Id id) {
        return getBaseMapper().getById(getTableName(tableId), id);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#count(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public int count(DynamicEntityQuery criterion) {
        return getBaseMapper().count(criterion);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#page(com.jarvis.framework.search.Page,
     *      com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public List<?> page(Page page, DynamicEntityQuery criterion) {
        final List<Entity> content = getBaseMapper().page(page, criterion);
        return processPageContent(content);
    }

    /**
     * 分页结果集处理
     *
     * @param content
     */
    protected List<?> processPageContent(List<Entity> content) {
        return content;
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#updateBy(com.jarvis.framework.mybatis.update.CriteriaUpdate)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateBy(CriteriaUpdate<String> criterion) {
        return getBaseMapper().updateBy(criterion);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteBy(com.jarvis.framework.mybatis.update.CriteriaDelete)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBy(DynamicEntityDelete criterion) {
        return getBaseMapper().deleteBy(criterion);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#getBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public Entity getBy(DynamicEntityQuery criterion) {
        return getBaseMapper().getBy(criterion);
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#queryBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public List<Entity> queryBy(DynamicEntityQuery criterion) {
        return getBaseMapper().queryBy(criterion);
    }

    /**
     * 创建一个空查询条件器
     *
     * @return
     */
    protected DynamicEntityQuery createCriteriaQuery() {
        return CriteriaQueryBuilder.createDynamicEntityCriterion();
    }

    /**
     * 创建一个空更新条件器
     *
     * @return
     */
    protected DynamicEntityUpdate createCriteriaUpdate() {
        return CriteriaUpdateBuilder.createDynamicCriterion();
    }

    /**
     * 创建一个空删除条件器
     *
     * @return
     */
    protected DynamicEntityDelete createCriteriaDelete() {
        return CriteriaDeleteBuilder.createDynamicCriterion();
    }

    /**
     *
     * @see com.jarvis.framework.web.service.BaseDynamicEntityService#exists(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public boolean exists(DynamicEntityQuery criterion) {
        return getBaseMapper().exists(criterion);
    }

}
