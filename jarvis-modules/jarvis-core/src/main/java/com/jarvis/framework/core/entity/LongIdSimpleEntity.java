package com.jarvis.framework.core.entity;

/**
 * 所有id为Long类型的实体类的超类（不包含中间表）
 * <p>描述：定义实体主键，要求每个实体表中必须包含id字段作为主键</p>
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月14日
 */
public interface LongIdSimpleEntity extends BaseSimpleEntity<Long> {
}
