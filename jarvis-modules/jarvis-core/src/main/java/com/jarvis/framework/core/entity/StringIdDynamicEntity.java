package com.jarvis.framework.core.entity;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.util.StringUtils;

import java.util.HashMap;

import static com.jarvis.framework.constant.BaseFieldConstant.ID;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月28日
 */
public class StringIdDynamicEntity extends HashMap<String, Object> implements BaseDynamicEntity<String> {

    /**
     *
     */
    private static final long serialVersionUID = 6612835614065881307L;

    /**
     * 动态数据对应的表名
     */
    private String tableName;

    /**
     * @see com.jarvis.framework.core.entity.BaseEntity#getId()
     */
    @Override
    public String getId() {
        return (String) get(ID);
    }

    /**
     * @see com.jarvis.framework.core.entity.BaseEntity#setId(java.io.Serializable)
     */
    @Override
    public void setId(String id) {
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
    public boolean isEmpytTable() {
        return !StringUtils.hasLength(tableName);
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

    protected String processId(Object value) {
        if (null != value) {
            return String.valueOf(value);
        }
        return null;
    }

}
