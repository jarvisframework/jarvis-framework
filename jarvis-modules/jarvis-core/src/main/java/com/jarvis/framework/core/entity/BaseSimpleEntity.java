package com.jarvis.framework.core.entity;

import java.io.Serializable;

/**
 * 简单实体对象超类
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public interface BaseSimpleEntity<Id extends Serializable> extends BaseIdPrimaryKeyEntity<Id> {

}
