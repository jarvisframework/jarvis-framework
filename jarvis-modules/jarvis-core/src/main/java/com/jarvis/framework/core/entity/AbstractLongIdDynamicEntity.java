package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;
import com.jarvis.framework.constant.BaseFieldConstant;

import java.time.LocalDateTime;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年8月6日
 */
public class AbstractLongIdDynamicEntity extends LongIdDynamicEntity implements BaseRevisionEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 8203852676621163224L;

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#getRevision()
     */
    @Override
    public Integer getRevision() {
        return (Integer) get(BaseFieldConstant.REVISION);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#setRevision(java.lang.Integer)
     */
    @Override
    public void setRevision(Integer revision) {
        put(BaseFieldConstant.REVISION, revision);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#getCreatedBy()
     */
    @Override
    @IgnoreUpdate
    public Long getCreatedBy() {
        return (Long) get(BaseFieldConstant.CREATED_BY);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#setCreatedBy(java.io.Serializable)
     */
    @Override
    public void setCreatedBy(Long createdBy) {
        put(BaseFieldConstant.CREATED_BY, createdBy);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#getCreatedTime()
     */
    @Override
    @IgnoreUpdate
    public LocalDateTime getCreatedTime() {
        return (LocalDateTime) get(BaseFieldConstant.CREATED_TIME);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#setCreatedTime(java.time.LocalDateTime)
     */
    @Override
    public void setCreatedTime(LocalDateTime createdTime) {
        put(BaseFieldConstant.CREATED_TIME, createdTime);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#getUpdatedBy()
     */
    @Override
    public Long getUpdatedBy() {
        return (Long) get(BaseFieldConstant.UPDATED_BY);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#setUpdatedBy(java.io.Serializable)
     */
    @Override
    public void setUpdatedBy(Long updatedBy) {
        put(BaseFieldConstant.UPDATED_BY, updatedBy);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#getUpdatedTime()
     */
    @Override
    public LocalDateTime getUpdatedTime() {
        return (LocalDateTime) get(BaseFieldConstant.UPDATED_TIME);
    }

    /**
     *
     * @see com.jarvis.framework.core.entity.BaseRevisionEntity#setUpdatedTime(java.time.LocalDateTime)
     */
    @Override
    public void setUpdatedTime(LocalDateTime updatedTime) {
        put(BaseFieldConstant.UPDATED_TIME, updatedTime);
    }

}
