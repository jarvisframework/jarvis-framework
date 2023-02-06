package com.jarvis.framework.mybatis.handler.support;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
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
public class LongIdSimpleSupportHandler implements EntityFillingSupportHandler, Ordered {

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#insert(com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity)
     */
    @Override
    public void insert(BaseIdPrimaryKeyEntity<?> entity) {
        final AbstractLongIdEntity fillingEntity = (AbstractLongIdEntity) entity;
        if (null == fillingEntity.getCreatedTime()) {
            fillingEntity.setCreatedTime(LocalDateTime.now());
        }
        if (null == fillingEntity.getUpdatedTime()) {
            fillingEntity.setUpdatedTime(LocalDateTime.now());
        }
        fillingEntity.setRevision(1);
        final SecurityUser user = SecurityUtil.getUser();
        if (null != user) {
            if (null == fillingEntity.getCreatedBy()) {
                fillingEntity.setCreatedBy(Long.parseLong(String.valueOf(user.getId())));
            }
            if (null == fillingEntity.getUpdatedBy()) {
                fillingEntity.setUpdatedBy(Long.parseLong(String.valueOf(user.getId())));
            }
        }
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.handler.EntityAutoFillingHandler#update(com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity)
     */
    @Override
    public void update(BaseIdPrimaryKeyEntity<?> entity) {
        final AbstractLongIdEntity fillingEntity = (AbstractLongIdEntity) entity;
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
        return AbstractLongIdEntity.class.isAssignableFrom(entity.getClass());
    }

    /**
     *
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return 200;
    }

}
