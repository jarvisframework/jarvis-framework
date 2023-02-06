package com.jarvis.framework.core.entity;

import java.io.Serializable;

/**
 * 所有有id主键的实体类的超类
 * <p>描述：定义实体主键，要求每个实体表中必须包含id字段作为主键</p>
 *
 * @author qiucs
 * @version 1.0.0 2021年1月21日
 */
public interface BaseIdPrimaryKeyEntity<Id extends Serializable> extends BaseEntity<Id> {

    Id getId();

    void setId(Id id);

}
