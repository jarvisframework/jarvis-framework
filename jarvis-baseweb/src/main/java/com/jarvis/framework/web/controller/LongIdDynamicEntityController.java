package com.jarvis.framework.web.controller;

import com.jarvis.framework.core.entity.LongIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.LongIdDynamicEntityMapper;
import com.jarvis.framework.web.service.LongIdDynamicEntityService;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public abstract class LongIdDynamicEntityController<Entity extends LongIdDynamicEntity,
                                                    Mapper extends LongIdDynamicEntityMapper<Entity>,
                                                    Service extends LongIdDynamicEntityService<Entity, Mapper>>
    extends BaseDynamicEntityController<Long, Entity, Mapper, Service> {

}
