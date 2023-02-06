package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;

import java.time.LocalDateTime;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月2日
 */
public abstract class AbstractLongIdEntity implements LongIdSimpleEntity, BaseRevisionEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = 5582956964873034052L;

    protected Long id;

    protected Integer revision;

    @IgnoreUpdate
    protected Long createdBy;

    @IgnoreUpdate
    protected LocalDateTime createdTime;

    protected Long updatedBy;

    protected LocalDateTime updatedTime;

    /**
     * @return the id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(Long id) {
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
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    @Override
    public void setCreatedBy(Long createdBy) {
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
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(Long updatedBy) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractLongIdEntity other = (AbstractLongIdEntity) obj;
        if (other.id == null) {
            return false;
        }
        if (other.id.equals(getId())) {
            return true;
        }
        return false;
    }

}
