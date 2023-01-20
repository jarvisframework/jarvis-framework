package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;

import java.time.LocalDateTime;

public abstract class AbstractStringIdEntity implements StringIdSimpleEntity, BaseRevisionEntity<String> {
    private static final long serialVersionUID = -7590648840056610260L;
    protected String id;
    protected Integer revision;
    @IgnoreUpdate
    protected String createdBy;
    @IgnoreUpdate
    protected LocalDateTime createdTime;
    protected String updatedBy;
    protected LocalDateTime updatedTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
