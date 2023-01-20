package com.jarvis.framework.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>基础修订实体</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-11-14 20:47:29
 */
public interface BaseRevisionEntity<Id extends Serializable> extends BaseIdPrimaryKeyEntity<Id> {

    Integer getRevision();

    void setRevision(Integer var1);

    Id getCreatedBy();

    void setCreatedBy(Id var1);

    LocalDateTime getCreatedTime();

    void setCreatedTime(LocalDateTime var1);

    Id getUpdatedBy();

    void setUpdatedBy(Id var1);

    LocalDateTime getUpdatedTime();

    void setUpdatedTime(LocalDateTime var1);
}
