package com.jarvis.framework.web.service.impl;

import com.jarvis.framework.core.entity.LongIdSimpleEntity;
import com.jarvis.framework.mybatis.mapper.LongIdSimpleEntityMapper;
import com.jarvis.framework.web.service.LongIdSimpleEntityService;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class LongIdSimpleEntityServiceImpl<Entity extends LongIdSimpleEntity, Mapper extends LongIdSimpleEntityMapper<Entity>>
    extends BaseSimpleEntityServiceImpl<Long, Entity, Mapper>
    implements LongIdSimpleEntityService<Entity, Mapper> {

}
