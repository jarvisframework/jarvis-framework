package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler;

import java.util.Iterator;
import java.util.List;

public class EntityAutoFillingDefaultHandler implements EntityAutoFillingHandler {
    private final List<EntityFillingSupportHandler> supportHandlers;

    public EntityAutoFillingDefaultHandler(List<EntityFillingSupportHandler> supportHandlers) {
        this.supportHandlers = supportHandlers;
    }

    public void insert(BaseIdPrimaryKeyEntity<?> entity) {
        Iterator var2 = this.supportHandlers.iterator();

        while(var2.hasNext()) {
            EntityFillingSupportHandler supportHandler = (EntityFillingSupportHandler)var2.next();
            if (supportHandler.support(entity)) {
                supportHandler.insert(entity);
                break;
            }
        }

    }

    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        Iterator var2 = this.supportHandlers.iterator();

        while(var2.hasNext()) {
            EntityFillingSupportHandler supportHandler = (EntityFillingSupportHandler)var2.next();
            if (supportHandler.support(entity)) {
                supportHandler.update(entity);
                break;
            }
        }

    }
}
