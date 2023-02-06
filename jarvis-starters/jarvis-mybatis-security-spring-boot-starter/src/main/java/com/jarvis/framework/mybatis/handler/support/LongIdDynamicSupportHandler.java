package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.AbstractLongIdDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.security.model.SecurityUser;
import com.jarvis.framework.security.util.SecurityUtil;
import org.springframework.core.Ordered;

import java.time.LocalDateTime;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年8月6日
 */
public class LongIdDynamicSupportHandler implements EntityFillingSupportHandler, Ordered {

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#insert(com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity)
     */
    @Override
    public void insert(BaseIdPrimaryKeyEntity<?> entity) {
        final AbstractLongIdDynamicEntity fillingEntity = (AbstractLongIdDynamicEntity) entity;
        fillingEntity.setCreatedTime(LocalDateTime.now());
        fillingEntity.setUpdatedTime(LocalDateTime.now());
        fillingEntity.setRevision(1);
        final SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            fillingEntity.setCreatedBy(Long.parseLong(String.valueOf(user.getId())));
            fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
        }
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#update(com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity)
     */
    @Override
    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        final AbstractLongIdDynamicEntity fillingEntity = (AbstractLongIdDynamicEntity) entity;
        fillingEntity.setUpdatedTime(LocalDateTime.now());
        Integer revision = fillingEntity.getRevision();
        fillingEntity.setRevision(null == revision ? 1 : revision++);
        final SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
        }
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.support.EntityFillingSupportHandler#support(com.jarvis.framework.core.entity.BaseEntity)
     */
    @Override
    public boolean support(BaseIdPrimaryKeyEntity<?> entity) {
        return AbstractLongIdDynamicEntity.class.isAssignableFrom(entity.getClass());
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return 100;
    }

}
