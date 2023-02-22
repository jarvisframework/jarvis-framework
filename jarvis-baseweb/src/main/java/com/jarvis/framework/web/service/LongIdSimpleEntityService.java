package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.LongIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.LongIdSimpleEntityMapper;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public interface LongIdSimpleEntityService<Entity extends LongIdSimpleEntity, Mapper extends LongIdSimpleEntityMapper<Entity>>
    extends BaseSimpleEntityService<Long, Entity, Mapper> {

}
