package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.util.SecurityUtil;
import org.springframework.core.Ordered;

import java.time.LocalDateTime;

public class LongIdSimpleSupportHandler implements EntityFillingSupportHandler, Ordered {

    public void insert(BaseIdPrimaryKeyEntity<?> entity) {
        AbstractLongIdEntity fillingEntity = (AbstractLongIdEntity)entity;
        if (null == fillingEntity.getCreatedTime()) {
            fillingEntity.setCreatedTime(LocalDateTime.now());
        }

        if (null == fillingEntity.getUpdatedTime()) {
            fillingEntity.setUpdatedTime(LocalDateTime.now());
        }

        fillingEntity.setRevision(1);
        SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            if (null == fillingEntity.getCreatedBy()) {
                fillingEntity.setCreatedBy(Long.parseLong(String.valueOf(user.getId())));
            }

            if (null == fillingEntity.getUpdatedBy()) {
                fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
            }
        }

    }

    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        AbstractLongIdEntity fillingEntity = (AbstractLongIdEntity)entity;
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
        return AbstractLongIdEntity.class.isAssignableFrom(entity.getClass());
    }

    public int getOrder() {
        return 200;
    }
}
