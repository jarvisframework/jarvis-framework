package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.mybatis.provider.DynamicEntityExecuteProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月19日
 */
public interface BaseDynamicEntityMapper<Id extends Serializable, Entity extends BaseDynamicEntity<Id>>
        extends BaseIdPrimaryKeyEnityMapper<Id, Entity, String> {

    /**
     * 根据ID查询
     *
     * @param tableName
     * @param id
     * @return
     */
    @SelectProvider(value = DynamicEntityExecuteProvider.class, method = "getById")
    Entity getById(@Param("tableName") String tableName, @Param("id") Id id);

    /**
     * 根据ID删除
     *
     * @param tableName
     * @param id
     * @return
     */
    @DeleteProvider(value = DynamicEntityExecuteProvider.class, method = "deleteById")
    boolean deleteById(@Param("tableName") String tableName, @Param("id") Id id);

    /**
     * 根据ids删除
     *
     * @param tableName
     * @param ids
     * @return boolean
     */
    @DeleteProvider(value = DynamicEntityExecuteProvider.class, method = "deleteByIds")
    boolean deleteByIds(@Param("tableName") String tableName, @Param("ids") Id[] arrayId);

}
