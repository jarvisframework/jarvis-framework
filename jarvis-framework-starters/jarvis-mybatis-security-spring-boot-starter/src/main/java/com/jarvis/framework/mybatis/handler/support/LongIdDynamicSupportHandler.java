package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.AbstractLongIdDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.util.SecurityUtil;
import org.springframework.core.Ordered;

import java.time.LocalDateTime;

public class LongIdDynamicSupportHandler implements EntityFillingSupportHandler, Ordered {
    public LongIdDynamicSupportHandler() {
    }

    public void insert(BaseIdPrimaryKeyEntity<?> entity) {
        AbstractLongIdDynamicEntity fillingEntity = (AbstractLongIdDynamicEntity)entity;
        fillingEntity.setCreatedTime(LocalDateTime.now());
        fillingEntity.setUpdatedTime(LocalDateTime.now());
        fillingEntity.setRevision(1);
        SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            fillingEntity.setCreatedBy(Long.parseLong(String.valueOf(user.getId())));
            fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
        }

    }

    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        AbstractLongIdDynamicEntity fillingEntity = (AbstractLongIdDynamicEntity)entity;
        fillingEntity.setUpdatedTime(LocalDateTime.now());
        Integer revision = fillingEntity.getRevision();
        int var10001;
        if (null == revision) {
            var10001 = 1;
        } else {
            revision + 1;
            var10001 = revision;
        }

        fillingEntity.setRevision(var10001);
        SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
        }

    }

    public boolean support(BaseIdPrimaryKeyEntity<?> entity) {
        return AbstractLongIdDynamicEntity.class.isAssignableFrom(entity.getClass());
    }

    public int getOrder() {
        return 100;
    }
}
