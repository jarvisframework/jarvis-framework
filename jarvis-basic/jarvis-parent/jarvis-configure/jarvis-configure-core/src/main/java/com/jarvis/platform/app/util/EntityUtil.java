package com.jarvis.platform.app.util;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import org.springframework.beans.BeanUtils;

/**
 * @author wangdh
 * @date 2022-09-06 19:35
 */
public class EntityUtil {
    /**
     * 复制实体
     *
     * @param t 实体
     * @return t
     */
    public static <T extends AbstractLongIdEntity> T copyEntity(T t) {
        try {
            AbstractLongIdEntity entity = t.getClass().newInstance();
            BeanUtils.copyProperties(t, entity);
            initPubProperty(entity);
            return (T) entity;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 清空复制的实体
     *
     * @param t 继承AbstractLongIdEntity的实体信息
     */
    public static <T extends AbstractLongIdEntity> void initPubProperty(T t) {
        t.setId(null);
        t.setCreatedBy(null);
        t.setCreatedTime(null);
        t.setRevision(0);
        t.setUpdatedBy(null);
        t.setUpdatedTime(null);
    }
}
