package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.LongIdDynamicEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public interface LongIdDynamicEntityMapper<Entity extends LongIdDynamicEntity>
        extends BaseDynamicEntityMapper<Long, Entity> {

}
