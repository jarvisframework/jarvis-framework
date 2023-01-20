package com.jarvis.framework.core.entity;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class LongIdDynamicEntity extends HashMap<String, Object> implements BaseDynamicEntity<Long> {
    private static final long serialVersionUID = -7454041455922739457L;
    private String tableName;

    public LongIdDynamicEntity() {
    }

    public Long getId() {
        return (Long)this.get("id");
    }

    public void setId(Long id) {
        super.put("id", id);
    }

    public String tableName() {
        if (!StringUtils.hasLength(this.tableName)) {
            throw new FrameworkException("动态实体表名(tableName)不能为空");
        } else {
            return this.tableName;
        }
    }

    public void tableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean hasTableName() {
        return !StringUtils.hasText(this.tableName);
    }

    public Object put(String key, Object value) {
        if ("id".equals(key)) {
            value = this.processId(value);
        }

        return super.put(key, value);
    }

    protected Long processId(Object value) {
        Long id = null;
        Class<?> clazz = value.getClass();
        if (Number.class.isAssignableFrom(clazz)) {
            id = ((Number)value).longValue();
        } else {
            try {
                id = Long.parseLong(String.valueOf(value));
            } catch (Exception var5) {
                throw new FrameworkException(String.format("动态表id字段值必须是long类型，错误信息[id=%s]", String.valueOf(value)));
            }
        }

        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
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
            LongIdDynamicEntity other = (LongIdDynamicEntity)obj;
            if (other.getId() == null) {
                return false;
            } else {
                return other.getId().equals(this.getId());
            }
        }
    }
}
