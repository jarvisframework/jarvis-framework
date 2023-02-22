package com.jarvis.framework.mybatis.mapper;

import com.jarvis.framework.core.entity.StringIdDynamicEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public interface StringIdDynamicEntityMapper<Entity extends StringIdDynamicEntity>
        extends BaseDynamicEntityMapper<String, Entity> {

}
