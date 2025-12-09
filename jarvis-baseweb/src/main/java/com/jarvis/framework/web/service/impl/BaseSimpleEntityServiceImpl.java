package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.mapper.BaseSimpleEntityMapper;
import com.jarvis.framework.mybatis.update.*;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.CriteriaQueryBuilder;
import com.jarvis.framework.search.EntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.framework.util.CamelCaseUtil;
import com.jarvis.framework.util.ColumnFunctionUtil;
import com.jarvis.framework.web.service.BaseSimpleEntityService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Doug Wang
 * @version 1.0.0 2021年1月26日
 */
public class BaseSimpleEntityServiceImpl<Id extends Serializable, Entity extends BaseSimpleEntity<Id>,
        Mapper extends BaseSimpleEntityMapper<Id, Entity>>
        implements BaseSimpleEntityService<Id, Entity, Mapper> {

    private final Class<Mapper> mapperClass = mapperClass();

    @Autowired
    protected Mapper baseMapper;

    @Autowired
    protected SqlSessionFactory sqlSessionFactory;

    protected Mapper getBaseMapper() {
        return this.baseMapper;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Class<Mapper> mapperClass() {
        final Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(getClass());
        final Type value = typeVariableMap.entrySet().stream().filter(e -> e.getKey().getName().equals("Mapper"))
                .findFirst().get()
                .getValue();
        return (Class<Mapper>) value;
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#insert(com.jarvis.framework.core.entity.BaseSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(Entity entity) {
        this.beforeInsert(entity);
        return getBaseMapper().insert(entity);
    }

    /**
     * 新增前业务处理
     *
     * @param entity
     */
    protected void beforeInsert(Entity entity) {

    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#insertAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> m.insert(e) ? 1 : 0);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#update(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(Entity entity) {
        this.beforeUpdate(entity);
        return getBaseMapper().update(entity);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#patch(com.jarvis.framework.core.entity.BaseSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean patch(Entity entity) {
        this.beforeUpdate(entity);
        CriteriaUpdate<Getter<Entity>> criteriaUpdate = new CriteriaUpdate<>();
        List<String> fields = PersistentUtil.getUpdatePersistentFields(entity.getClass());
        boolean hasUpdate = false;
        for (String field : fields) {
            Method method = ReflectionUtils.findMethod(entity.getClass(), "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1));
            if (null == method) {
                method = ReflectionUtils.findMethod(entity.getClass(), "is" + Character.toUpperCase(field.charAt(0)) + field.substring(1));
            }
            if (null != method) {
                Object value = ReflectionUtils.invokeMethod(method, entity);
                if (null != value) {
                    criteriaUpdate.getData().put(PersistentUtil.fieldToColumn(field), value);
                    hasUpdate = true;
                }
            }
        }

        if (!hasUpdate) {
            return false;
        }

        criteriaUpdate.filter(c -> {
            c.equal(BaseSimpleEntity::getId, entity.getId());
        });
        return getBaseMapper().updateBy(criteriaUpdate) > 0;
    }

    /**
     * 修改前业务处理
     *
     * @param entity
     */
    protected void beforeUpdate(Entity entity) {

    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#updateAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> m.update(e) ? 1 : 0);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#delete(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Entity entity) {
        return getBaseMapper().delete(entity);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteAll(java.util.Collection)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteAll(Collection<Entity> entities) {
        return batch(entities, (e, m) -> m.delete(e) ? 1 : 0);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteById(java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteById(Id id) {
        this.beforeDelete(id);
        return getBaseMapper().deleteById(id);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteByIds(java.util.List)
     */
    @Override
    public int deleteByIds(Collection<Id> ids) {
        return batch(ids, (id, m) -> {
            return m.deleteById(id) ? 1 : 0;
        });
    }

    /**
     * 删除前业务处理
     *
     * @param id
     */
    protected void beforeDelete(Id id) {

    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#batch(java.util.Collection, java.util.function.BiFunction, int)
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
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#batch(java.util.Collection, java.util.function.BiFunction)
     */
    @Override
    public <T> int batch(Collection<T> data, BiFunction<T, Mapper, Integer> fn) {
        return batch(data, fn, 512);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#getById(java.lang.Long)
     */
    @Override
    public Entity getById(Id id) {
        return getBaseMapper().getById(id);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#count(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public int count(CriteriaQuery<Getter<Entity>> criterion) {
        return getBaseMapper().count(criterion);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#page(com.jarvis.framework.search.Page,
     * com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public List<?> page(Page page, CriteriaQuery<Getter<Entity>> criterion) {
        final List<Entity> content = getBaseMapper().page(page, criterion);

        page.setContent(processPageContent(content));
        page.setSummary(processPageSummary(content));
        return page.getContent();
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
     * 汇总结果处理
     *
     * @param content
     */
    protected Map<String, Object> processPageSummary(List<Entity> content) {
        List<Getter<Entity>> getters = this.addSummaryParameter();

        if (!CollectionUtils.isEmpty(getters) && !CollectionUtils.isEmpty(content)) {
            Map<String, Object> summary = new HashMap<>();
            for (final Getter<Entity> getter : getters) {
                String column = ColumnFunctionUtil.toColumn(getter);
                String summaryKey = "total" + CamelCaseUtil.lowerToUpperCamelCase(column);

                Object firstValue = getter.apply(content.get(0));
                if (firstValue instanceof Number) {
                    // 数值类型汇总
                    double sum = content.stream()
                            .mapToDouble(entity -> {
                                Object value = getter.apply(entity);
                                return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
                            })
                            .sum();
                    summary.put(summaryKey, sum);
                } else if (firstValue instanceof Collection) {
                    // 集合类型统计数量
                    long count = content.stream()
                            .mapToLong(entity -> {
                                Collection<?> collection = (Collection<?>) getter.apply(entity);
                                return collection != null ? collection.size() : 0L;
                            })
                            .sum();
                    summary.put(summaryKey, count);
                } else {
                    // 其他类型统计非空数量
                    long count = content.stream()
                            .filter(entity -> getter.apply(entity) != null)
                            .count();
                    summary.put(summaryKey, count);
                }
            }
            return summary;
        }

        return null;
    }

    protected List<Getter<Entity>> addSummaryParameter() {
        return null;
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#updateBy(com.jarvis.framework.mybatis.update.CriteriaUpdate)
     */
    @Override
    public int updateBy(CriteriaUpdate<Getter<Entity>> criterion) {
        return getBaseMapper().updateBy(criterion);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#deleteBy(com.jarvis.framework.mybatis.update.CriteriaDelete)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBy(CriteriaDelete<Getter<Entity>> criterion) {
        return getBaseMapper().deleteBy(criterion);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#getBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public Entity getBy(CriteriaQuery<Getter<Entity>> criterion) {
        return getBaseMapper().getBy(criterion);
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#queryBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public List<Entity> queryBy(CriteriaQuery<Getter<Entity>> criterion) {
        return getBaseMapper().queryBy(criterion);
    }

    /**
     * 创建一个空查询条件器
     *
     * @return
     */
    protected EntityQuery<Entity> createCriteriaQuery() {
        return CriteriaQueryBuilder.<Entity>createEntityCriterion();
    }

    /**
     * 创建一个空更新条件器
     *
     * @return
     */
    protected EntityUpdate<Entity> createCriteriaUpdate() {
        return CriteriaUpdateBuilder.<Entity>createEntityCriterion();
    }

    /**
     * 创建一个空删除条件器
     *
     * @return
     */
    protected EntityDelete<Entity> createCriteriaDelete() {
        return CriteriaDeleteBuilder.<Entity>createEntityCriterion();
    }

    /**
     * @see com.jarvis.framework.web.service.BaseSimpleEntityService#exists(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public boolean exists(CriteriaQuery<Getter<Entity>> criterion) {
        return getBaseMapper().exists(criterion);
    }

}
