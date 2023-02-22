package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;

import java.time.LocalDateTime;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public abstract class AbstractStringIdEntity implements StringIdSimpleEntity, BaseRevisionEntity<String> {

    /**
     *
     */
    private static final long serialVersionUID = -7590648840056610260L;

    protected String id;

    protected Integer revision;

    @IgnoreUpdate
    protected String createdBy;

    @IgnoreUpdate
    protected LocalDateTime createdTime;

    protected String updatedBy;

    protected LocalDateTime updatedTime;

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the revision
     */
    @Override
    public Integer getRevision() {
        return revision;
    }

    /**
     * @param revision the revision to set
     */
    @Override
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    /**
     * @return the createdBy
     */
    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdTime
     */
    @Override
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime the createdTime to set
     */
    @Override
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedTime
     */
    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime the updatedTime to set
     */
    @Override
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

}
