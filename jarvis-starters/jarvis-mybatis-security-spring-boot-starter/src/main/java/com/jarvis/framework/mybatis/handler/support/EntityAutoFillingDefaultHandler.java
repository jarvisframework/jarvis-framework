package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler;

import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月6日
 */
public class EntityAutoFillingDefaultHandler implements EntityAutoFillingHandler {

    private final List<EntityFillingSupportHandler> supportHandlers;

    public EntityAutoFillingDefaultHandler(List<EntityFillingSupportHandler> supportHandlers) {
        this.supportHandlers = supportHandlers;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#insert(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Override
    public void insert(BaseIdPrimaryKeyEntity<?> entity) {

        for (final EntityFillingSupportHandler supportHandler : supportHandlers) {
            if (!supportHandler.support(entity)) {
                continue;
            }
            supportHandler.insert(entity);
            break;
        }

    }

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#update(com.jarvis.framework.core.entity.LongIdSimpleEntity)
     */
    @Override
    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        for (final EntityFillingSupportHandler supportHandler : supportHandlers) {
            if (!supportHandler.support(entity)) {
                continue;
            }
            supportHandler.update(entity);
            break;
        }
    }

}
