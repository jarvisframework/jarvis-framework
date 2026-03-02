package com.jarvis.framework.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Doug Wang
 * @version 1.0.0 2021年8月6日
 */
public interface BaseRevisionEntity<Id extends Serializable> extends BaseIdPrimaryKeyEntity<Id> {

    int getRevision();

    /**
     * @param revision the revision to set
     */
    void setRevision(int revision);

    /**
     * @return the createdBy
     */
    Id getCreatedBy();

    /**
     * @param createdBy the createdBy to set
     */
    void setCreatedBy(Id createdBy);

    /**
     * @return the creator
     */
    String getCreator();

    /**
     * @param creator the creator to set
     */
    void setCreator(String creator);

    /**
     * @return the createdTime
     */
    LocalDateTime getCreatedTime();

    /**
     * @param createdTime the createdTime to set
     */
    void setCreatedTime(LocalDateTime createdTime);

    /**
     * @return the updatedBy
     */
    Id getUpdatedBy();

    /**
     * @param updatedBy the updatedBy to set
     */
    void setUpdatedBy(Id updatedBy);

    /**
     * @return the updater
     */
    String getUpdater();

    /**
     * @param updater the updater to set
     */
    void setUpdater(String updater);

    /**
     * @return the updatedTime
     */
    LocalDateTime getUpdatedTime();

    /**
     * @param updatedTime the updatedTime to set
     */
    void setUpdatedTime(LocalDateTime updatedTime);
}
