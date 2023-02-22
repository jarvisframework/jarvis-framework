package com.jarvis.framework.web.service;

import com.jarvis.framework.core.entity.StringIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.StringIdDynamicEntityMapper;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public interface StringIdDynamicEntityService<Entity extends StringIdDynamicEntity, Mapper extends StringIdDynamicEntityMapper<Entity>>
    extends BaseDynamicEntityService<String, Entity, Mapper> {

}
