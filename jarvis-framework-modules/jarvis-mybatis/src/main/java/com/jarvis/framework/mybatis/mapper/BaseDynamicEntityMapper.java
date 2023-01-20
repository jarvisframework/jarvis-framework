package com.jarvis.framework.mybatis.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

public interface BaseDynamicEntityMapper<Id extends Serializable, Entity extends BaseDynamicEntity<Id>> extends BaseIdPrimaryKeyEnityMapper<Id, Entity, String> {
    @SelectProvider(
        value = DynamicEntityExecuteProvider.class,
        method = "getById"
    )
    Entity getById(@Param("tableName") String var1, @Param("id") Id var2);

    @DeleteProvider(
        value = DynamicEntityExecuteProvider.class,
        method = "deleteById"
    )
    boolean deleteById(@Param("tableName") String var1, @Param("id") Id var2);

    @DeleteProvider(
        value = DynamicEntityExecuteProvider.class,
        method = "deleteByIds"
    )
    boolean deleteByIds(@Param("tableName") String var1, @Param("ids") Id[] var2);
}
