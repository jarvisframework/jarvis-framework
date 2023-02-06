package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.StringIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.StringIdSimpleEntityMapper;
import com.jarvis.framework.web.service.BaseSimpleEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class StringIdSimpleEntityServiceImpl<Entity extends StringIdSimpleEntity, Mapper extends StringIdSimpleEntityMapper<Entity>>
    extends BaseSimpleEntityServiceImpl<String, Entity, Mapper>
    implements BaseSimpleEntityService<String, Entity, Mapper> {

}
