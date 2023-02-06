package com.jarvis.framework.core.entity;

import java.io.Serializable;

/**
 * 所有中间表（关联表）实体类的超类（无ID主键），如：多对多关系对应的中间表
 *
 * @author qiucs
 * @version 1.0.0 2021年1月14日
 */
public interface BaseMiddleEntity<Id extends Serializable> extends BaseEntity<Id> {

}
