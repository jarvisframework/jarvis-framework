package com.jarvis.framework.core.entity;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.util.StringUtils;

import java.util.HashMap;

import static com.jarvis.framework.constant.BaseFieldConstant.ID;

/**
 * 所有动态表对应的实体基类
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月14日
 */
public class LongIdDynamicEntity extends HashMap<String, Object> implements BaseDynamicEntity<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -7454041455922739457L;

    /**
     * 动态数据对应的表名
     */
    private String tableName;

    /**
     * @see com.jarvis.framework.core.entity.BaseEntity#getId()
     */
    @Override
    public Long getId() {
        return (Long) get(ID);
    }

    /**
     * @see com.jarvis.framework.core.entity.BaseEntity#setId(java.io.Serializable)
     */
    @Override
    public void setId(Long id) {
        super.put(ID, id);
    }

    /**
     * @return the tableName
     */
    @Override
    public String tableName() {
        if (!StringUtils.hasLength(tableName)) {
            throw new FrameworkException("动态实体表名(tableName)不能为空");
        }
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    @Override
    public void tableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * tableName 是否为空
     *
     * @return
     */
    public boolean hasTableName() {
        return !StringUtils.hasText(tableName);
    }

    /**
     * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public Object put(String key, Object value) {
        if (ID.equals(key)) {
            value = processId(value);
        }
        return super.put(key, value);
    }

    protected Long processId(Object value) {
        Long id = null;
        final Class<?> clazz = value.getClass();
        if (Number.class.isAssignableFrom(clazz)) {
            id = ((Number) value).longValue();
        } else {
            try {
                id = Long.parseLong(String.valueOf(value));
            } catch (final Exception e) {
                throw new FrameworkException(String.format("动态表id字段值必须是long类型，错误信息[id=%s]", String.valueOf(value)));
            }
        }
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
        final LongIdDynamicEntity other = (LongIdDynamicEntity) obj;
        if (other.getId() == null) {
            return false;
        }
        if (other.getId().equals(getId())) {
            return true;
        }
        return false;
    }

}
