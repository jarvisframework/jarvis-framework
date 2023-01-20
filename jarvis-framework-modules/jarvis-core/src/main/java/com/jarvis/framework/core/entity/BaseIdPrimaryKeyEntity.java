package com.jarvis.framework.core.entity;

import java.io.Serializable;

/**
 * <p>基础ID主键实体</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-11-14 20:47:29
 */
public interface BaseIdPrimaryKeyEntity<Id extends Serializable> extends BaseEntity<Id> {

    Id getId();

    void setId(Id var1);
}
