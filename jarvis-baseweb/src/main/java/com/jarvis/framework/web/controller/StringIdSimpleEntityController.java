package com.jarvis.framework.web.controller;

import com.jarvis.framework.core.entity.StringIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.StringIdSimpleEntityMapper;
import com.jarvis.framework.web.service.StringIdSimpleEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class StringIdSimpleEntityController<Entity extends StringIdSimpleEntity,
                                            Mapper extends StringIdSimpleEntityMapper<Entity>,
                                            Service extends StringIdSimpleEntityService<Entity, Mapper>>
    extends BaseSimpleEntityController<String, Entity, Mapper, Service> {

}
