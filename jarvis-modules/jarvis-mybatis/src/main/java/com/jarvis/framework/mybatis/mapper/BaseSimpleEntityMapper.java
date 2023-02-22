package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.function.Getter;
import com.jarvis.framework.mybatis.provider.SimpleEntityExecuteProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;

/**
 * 简单的实体对应的基类Mapper
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月19日
 */
public interface BaseSimpleEntityMapper<Id extends Serializable, Entity extends BaseSimpleEntity<Id>>
        extends BaseIdPrimaryKeyEnityMapper<Id, Entity, Getter<Entity>> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @SelectProvider(value = SimpleEntityExecuteProvider.class, method = "getById")
    Entity getById(Id id);

    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @DeleteProvider(value = SimpleEntityExecuteProvider.class, method = "deleteById")
    boolean deleteById(Id id);

    /**
     * 根据多个ID删除
     *
     * @param ids ids
     * @return boolean
     */
    @DeleteProvider(value = SimpleEntityExecuteProvider.class, method = "deleteByIds")
    boolean deleteByIds(@Param("ids") Id[] ids);

}
