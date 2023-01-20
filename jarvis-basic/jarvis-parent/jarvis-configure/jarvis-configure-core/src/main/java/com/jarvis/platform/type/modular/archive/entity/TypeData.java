package com.jarvis.platform.type.modular.archive.entity;

import com.jarvis.framework.core.entity.AbstractLongIdDynamicEntity;
import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.util.CamelCaseUtil;
import io.swagger.annotations.ApiModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月26日
 */
@ApiModel("档案门类数据")
public class TypeData extends AbstractLongIdDynamicEntity {

    /**
     *
     */
    private static final long serialVersionUID = -7007004767651328833L;

    private static final String TABLE_ID_KEY = "@tableId@";

    private static final String CLASS_KEY = "@class";

    private static final String OWNER_ID_KEY = "ownerId";

    private static final String COLUMN_LABEL_KEY = "@columnLabels@";

    @Override
    public Collection<String> exclude() {
        return Lists.newArrayList(TABLE_ID_KEY, COLUMN_LABEL_KEY);
    }

    /**
     * 添加标签与字段关系
     *
     * @param columnLabel 字段标签
     * @param columnName 字段名称
     * @return TypeData
     */
    @SuppressWarnings("unchecked")
    public TypeData columnLabel(String columnLabel, String columnName) {
        Map<String, String> columnLabels = (Map<String, String>) get(COLUMN_LABEL_KEY);
        if (null == columnLabels) {
            columnLabels = new HashMap<>();
            super.put(COLUMN_LABEL_KEY, columnLabels);
        }
        columnLabels.put(CamelCaseUtil.toLowerCamelCase(columnLabel), CamelCaseUtil.toLowerCamelCase(columnName));
        return this;
    }

    /**
     * 添加标签与字段关系
     *
     * @param columnLabel 字段标签
     * @param columnName 字段名称
     * @return TypeData
     */
    @SuppressWarnings("unchecked")
    public TypeData columnLabels(Map<String, String> columnLabels) {
        if (null == columnLabels || columnLabels.isEmpty()) {
            return this;
        }
        if (!containsKey(COLUMN_LABEL_KEY)) {
            super.put(COLUMN_LABEL_KEY, new HashMap<>());
        }
        final Map<String, String> cls = (Map<String, String>) get(COLUMN_LABEL_KEY);
        columnLabels.forEach((k, v) -> {
            cls.put(CamelCaseUtil.toLowerCamelCase(k), CamelCaseUtil.toLowerCamelCase(v));
        });
        return this;
    }

    /**
     * @see com.gdda.archives.framework.core.entity.LongIdDynamicEntity#put(java.lang.String, java.lang.Object)
     */
    @Override
    public Object put(String key, Object value) {
        if (CLASS_KEY.equals(key)) {
            return null;
        }
        if (COLUMN_LABEL_KEY.equals(key) && !(value instanceof Map)) {
            throw new BusinessException(String.format("添加[key=%s]的值必须为Map对象", COLUMN_LABEL_KEY));
        }
        final Map<String, String> columnLabels = (Map<String, String>) super.get(COLUMN_LABEL_KEY);
        if (null != columnLabels) {
            final String prop = columnLabels.get(key);
            if (null != prop) {
                return super.put(prop, value);
            }
        }
        return super.put(key, value);
    }

    /**
     *
     * @see java.util.HashMap#get(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object get(Object key) {
        Object value = super.get(key);
        if (null != value) {
            return value;
        }
        final Map<String, String> columnLabels = (Map<String, String>) super.get(COLUMN_LABEL_KEY);
        if (null != columnLabels) {
            final String prop = columnLabels.get(key);
            if (null != prop) {
                value = get(prop);
            }
        }
        return value;
    }

    /**
     * 设置tabelId
     *
     * @param tableId 表ID
     * @return TypeData
     */
    /*public TypeData tableId(Long tableId) {
        put(TABLE_ID_KEY, tableId);
        return this;
    }*/

    /**
     * 获取ownerId
     *
     * @return Long
     */
    public Long ownerId() {
        final Object object = get(OWNER_ID_KEY);
        if (null == object) {
            return null;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        return Long.parseLong(object.toString());
    }

    /**
     * 获取字段标签集合
     *
     * @return Map
     */
    public Map<String, String> columnLabels() {
        Map<String, String> columnLabels = (Map<String, String>) get(COLUMN_LABEL_KEY);
        if (null == columnLabels) {
            columnLabels = new HashMap<>();
            super.put(COLUMN_LABEL_KEY, columnLabels);
        }
        return columnLabels;
    }

}
