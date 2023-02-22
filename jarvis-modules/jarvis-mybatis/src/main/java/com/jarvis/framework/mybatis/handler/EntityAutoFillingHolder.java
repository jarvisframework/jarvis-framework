package com.jarvis.framework.mybatis.handler;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月25日
 */
public class EntityAutoFillingHolder {

    private static EntityAutoFillingHandler entityAutoFillingHandler;

    public static void insert(BaseIdPrimaryKeyEntity<?> entity) {
        if (null == entityAutoFillingHandler) {
            return;
        }
        entityAutoFillingHandler.insert(entity);
    }

    public static void update(BaseIdPrimaryKeyEntity<?> entity) {
        if (null == entityAutoFillingHandler) {
            return;
        }
        entityAutoFillingHandler.update(entity);
    }

    public static void setHandler(EntityAutoFillingHandler handler) {
        entityAutoFillingHandler = handler;
    }

}
