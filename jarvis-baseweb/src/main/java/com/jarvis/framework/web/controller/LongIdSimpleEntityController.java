package com.jarvis.framework.web.controller;

import com.jarvis.framework.core.entity.LongIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.LongIdSimpleEntityMapper;
import com.jarvis.framework.web.service.LongIdSimpleEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class LongIdSimpleEntityController<Entity extends LongIdSimpleEntity,
                                          Mapper extends LongIdSimpleEntityMapper<Entity>,
                                          Service extends LongIdSimpleEntityService<Entity, Mapper>>
    extends BaseSimpleEntityController<Long, Entity, Mapper, Service> {

}
