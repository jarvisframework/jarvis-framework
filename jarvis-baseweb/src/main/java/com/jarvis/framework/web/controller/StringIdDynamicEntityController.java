package com.jarvis.framework.web.controller;

import com.jarvis.framework.core.entity.StringIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.StringIdDynamicEntityMapper;
import com.jarvis.framework.web.service.StringIdDynamicEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public abstract class StringIdDynamicEntityController<Entity extends StringIdDynamicEntity,
                                                      Mapper extends StringIdDynamicEntityMapper<Entity>,
                                                      Service extends StringIdDynamicEntityService<Entity, Mapper>>
    extends BaseDynamicEntityController<String, Entity, Mapper, Service> {

}
