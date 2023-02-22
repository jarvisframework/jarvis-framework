package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.LongIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.LongIdDynamicEntityMapper;
import com.jarvis.framework.web.service.LongIdDynamicEntityService;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public abstract class LongIdDynamicEntityServiceImpl<Entity extends LongIdDynamicEntity,
                                                     Mapper extends LongIdDynamicEntityMapper<Entity>>
    extends BaseDynamicEntityServiceImpl<Long, Entity, Mapper>
    implements LongIdDynamicEntityService<Entity, Mapper> {

}
