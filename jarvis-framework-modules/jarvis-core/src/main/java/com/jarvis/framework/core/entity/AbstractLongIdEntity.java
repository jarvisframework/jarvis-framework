package com.jarvis.framework.core.entity;

import com.jarvis.framework.annotation.IgnoreUpdate;

import java.time.LocalDateTime;

/**
 * <p>抽象长ID实体</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-11-14 20:47:29
 */
public abstract class AbstractLongIdEntity implements LongIdSimpleEntity, BaseRevisionEntity<Long>  {

    private static final long serialVersionUID = 5582956964873034052L;

    protected Long id;

    protected Integer revision;

    @IgnoreUpdate
    protected Long createdBy;

    @IgnoreUpdate
    protected LocalDateTime createdTime;

    protected Long updatedBy;

    protected LocalDateTime updatedTime;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getRevision() {
        return this.revision;
    }

    @Override
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Override
    public Long getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    @Override
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    @Override
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return this.updatedTime;
    }

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            AbstractLongIdEntity other = (AbstractLongIdEntity)obj;
            if (other.id == null) {
                return false;
            } else {
                return other.id.equals(this.getId());
            }
        }
    }

}
