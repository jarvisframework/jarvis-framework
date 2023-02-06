package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.StringIdDynamicEntity;
import com.jarvis.framework.mybatis.mapper.StringIdDynamicEntityMapper;
import com.jarvis.framework.web.service.StringIdDynamicEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public abstract class StringIdDynamicEntityServiceImpl<Entity extends StringIdDynamicEntity,
                                                       Mapper extends StringIdDynamicEntityMapper<Entity>>
    extends BaseDynamicEntityServiceImpl<String, Entity, Mapper>
    implements StringIdDynamicEntityService<Entity, Mapper> {

}
