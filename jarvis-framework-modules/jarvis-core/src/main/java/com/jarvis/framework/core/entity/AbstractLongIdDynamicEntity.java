package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;

import java.time.LocalDateTime;

public class AbstractLongIdDynamicEntity extends LongIdDynamicEntity implements BaseRevisionEntity<Long> {
    private static final long serialVersionUID = 8203852676621163224L;

    public AbstractLongIdDynamicEntity() {
    }

    public Integer getRevision() {
        return (Integer)this.get("revision");
    }

    public void setRevision(Integer revision) {
        this.put("revision", revision);
    }

    @IgnoreUpdate
    public Long getCreatedBy() {
        return (Long)this.get("createdBy");
    }

    public void setCreatedBy(Long createdBy) {
        this.put("createdBy", createdBy);
    }

    @IgnoreUpdate
    public LocalDateTime getCreatedTime() {
        return (LocalDateTime)this.get("createdTime");
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.put("createdTime", createdTime);
    }

    public Long getUpdatedBy() {
        return (Long)this.get("updatedBy");
    }

    public void setUpdatedBy(Long updatedBy) {
        this.put("updatedBy", updatedBy);
    }

    public LocalDateTime getUpdatedTime() {
        return (LocalDateTime)this.get("updatedTime");
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.put("updatedTime", updatedTime);
    }
}
