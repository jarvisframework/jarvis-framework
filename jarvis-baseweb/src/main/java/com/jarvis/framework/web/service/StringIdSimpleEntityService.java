package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.StringIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.StringIdSimpleEntityMapper;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public interface StringIdSimpleEntityService<Entity extends StringIdSimpleEntity, Mapper extends StringIdSimpleEntityMapper<Entity>>
    extends BaseSimpleEntityService<String, Entity, Mapper> {

}
