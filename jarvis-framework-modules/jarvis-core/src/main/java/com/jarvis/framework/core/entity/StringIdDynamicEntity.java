package com.jarvis.framework.core.entity;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.util.StringUtils;

import java.util.HashMap;

public class StringIdDynamicEntity extends HashMap<String, Object> implements BaseDynamicEntity<String> {

    private static final long serialVersionUID = 6612835614065881307L;

    private String tableName;

    public String getId() {
        return (String)this.get("id");
    }

    public void setId(String id) {
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

    public boolean isEmpytTable() {
        return !StringUtils.hasLength(this.tableName);
    }

    public Object put(String key, Object value) {
        if ("id".equals(key)) {
            value = this.processId(value);
        }

        return super.put(key, value);
    }

    protected String processId(Object value) {
        return null != value ? String.valueOf(value) : null;
    }
}
