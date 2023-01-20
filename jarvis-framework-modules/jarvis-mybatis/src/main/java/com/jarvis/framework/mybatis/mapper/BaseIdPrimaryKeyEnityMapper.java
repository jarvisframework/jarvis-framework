package com.jarvis.framework.mybatis.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseIdPrimaryKeyEnityMapper<Id extends Serializable, Entity extends BaseIdPrimaryKeyEntity<Id>, Column> {
    @InsertProvider(
        value = BaseEntityExecuteProvider.class,
        method = "insert"
    )
    boolean insert(Entity var1);

    @InsertProvider(
        value = BaseEntityExecuteProvider.class,
        method = "insertAll"
    )
    int insertAll(Collection<Entity> var1);

    @UpdateProvider(
        value = BaseEntityExecuteProvider.class,
        method = "update"
    )
    boolean update(Entity var1);

    @UpdateProvider(
        value = BaseEntityExecuteProvider.class,
        method = "updateAll"
    )
    int updateAll(Collection<Entity> var1);

    @DeleteProvider(
        value = BaseEntityExecuteProvider.class,
        method = "delete"
    )
    boolean delete(Entity var1);

    @DeleteProvider(
        value = BaseEntityExecuteProvider.class,
        method = "deleteAll"
    )
    int deleteAll(Collection<Entity> var1);

    @UpdateProvider(
        value = BaseEntityExecuteProvider.class,
        method = "updateBy"
    )
    int updateBy(CriteriaUpdate<Column> var1);

    @DeleteProvider(
        value = BaseEntityExecuteProvider.class,
        method = "deleteBy"
    )
    int deleteBy(CriteriaDelete<Column> var1);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "getBy"
    )
    Entity getBy(CriteriaQuery<Column> var1);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "getObjectBy"
    )
    <T> T getObjectBy(@Param("criterion") CriteriaQuery<Column> var1, @Param("clazz") Class<T> var2);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "getByMultiple"
    )
    <T> T getByMultiple(@Param("criterion") MultipleQuery var1, @Param("clazz") Class<T> var2);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "queryBy"
    )
    List<Entity> queryBy(CriteriaQuery<Column> var1);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "queryObjectsBy"
    )
    <T> List<T> queryObjectsBy(@Param("criterion") CriteriaQuery<Column> var1, @Param("clazz") Class<T> var2);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "queryByMultiple"
    )
    <T> List<T> queryByMultiple(@Param("criterion") MultipleQuery var1, @Param("clazz") Class<T> var2);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "count"
    )
    int count(CriteriaQuery<Column> var1);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "page"
    )
    List<Entity> page(@Param("page") Page var1, @Param("criterion") CriteriaQuery<Column> var2);

    @SelectProvider(
        value = BaseEntityExecuteProvider.class,
        method = "exists"
    )
    boolean exists(CriteriaQuery<Column> var1);
}
