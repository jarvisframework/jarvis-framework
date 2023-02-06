package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.LongIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.LongIdDynamicEntityMapper;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public interface LongIdDynamicEntityService<Entity extends LongIdDynamicEntity, Mapper extends LongIdDynamicEntityMapper<Entity>>
    extends BaseDynamicEntityService<Long, Entity, Mapper> {

}
