package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.provider.SimpleEntityExecuteProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

public interface BaseSimpleEntityMapper<Id extends Serializable, Entity extends BaseSimpleEntity<Id>> extends BaseIdPrimaryKeyEnityMapper<Id, Entity, Getter<Entity>> {
    @SelectProvider(
        value = SimpleEntityExecuteProvider.class,
        method = "getById"
    )
    Entity getById(Id var1);

    @DeleteProvider(
        value = SimpleEntityExecuteProvider.class,
        method = "deleteById"
    )
    boolean deleteById(Id var1);

    @DeleteProvider(
        value = SimpleEntityExecuteProvider.class,
        method = "deleteByIds"
    )
    boolean deleteByIds(@Param("ids") Id[] var1);
}
