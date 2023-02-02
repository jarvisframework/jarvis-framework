package com.jarvis.framework.mybatis.handler;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

public class EntityAutoFillingHolder {
    private static EntityAutoFillingHandler entityAutoFillingHandler;

    public EntityAutoFillingHolder() {
    }

    public static void insert(BaseIdPrimaryKeyEntity<?> entity) {
        if (null != entityAutoFillingHandler) {
            entityAutoFillingHandler.insert(entity);
        }
    }

    public static void update(BaseIdPrimaryKeyEntity<?> entity) {
        if (null != entityAutoFillingHandler) {
            entityAutoFillingHandler.update(entity);
        }
    }

    public static void setHandler(EntityAutoFillingHandler handler) {
        entityAutoFillingHandler = handler;
    }
}
