package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.core.exception.FrameworkException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperEntityUtil {
    private static final Map<Class<?>, String> ENTITY_INSERT_SQL_CACHE = new ConcurrentHashMap(16);
    private static final Map<Class<?>, String> ENTITY_UPDATE_SQL_CACHE = new ConcurrentHashMap(16);
    private static final Map<Class<?>, String> ENTITY_DELETE_SQL_CACHE = new ConcurrentHashMap(16);

    public static String mapperEntity2InsertSql(BaseIdPrimaryKeyEntity<?> entity) {
        // todo 编译错误
        /*Class<?> clazz = entity.getClass();
        if (null == entity.getId()) {
            PersistentUtil.setEntityIdPrimaryKey(entity);
        }

        EntityAutoFillingHolder.insert(entity);
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicInsertSql((BaseDynamicEntity)entity);
        } else {
            String sql = (String)ENTITY_INSERT_SQL_CACHE.get(clazz);
            if (null != sql) {
                return sql;
            } else {
                sql = entityInsertSql(clazz);
                ENTITY_INSERT_SQL_CACHE.putIfAbsent(clazz, sql);
                return sql;
            }
        }*/
        return null;
    }

    public static String mapperEntity2UpdateSql(BaseIdPrimaryKeyEntity<?> entity) {
        // todo 编译错误
        /*Class<?> clazz = entity.getClass();
        EntityAutoFillingHolder.update(entity);
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicUpdateSql((BaseDynamicEntity)entity);
        } else {
            String sql = (String)ENTITY_UPDATE_SQL_CACHE.get(clazz);
            if (null != sql) {
                return sql;
            } else {
                sql = entityUpdateSql(clazz);
                ENTITY_UPDATE_SQL_CACHE.putIfAbsent(clazz, sql);
                return sql;
            }
        }*/
        return null;
    }

    public static String mapperEntity2DeleteSql(BaseIdPrimaryKeyEntity<?> entity) {
        // todo 编译错误
        /*Class<?> clazz = entity.getClass();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicDeleteSql((BaseDynamicEntity)entity);
        } else {
            String sql = (String)ENTITY_DELETE_SQL_CACHE.get(clazz);
            if (null != sql) {
                return sql;
            } else {
                sql = entityDeleteSql(clazz);
                ENTITY_DELETE_SQL_CACHE.putIfAbsent(clazz, sql);
                return sql;
            }
        }*/
        return null;
    }

    private static String dynamicInsertSql(BaseDynamicEntity<?> entity) {
        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        String tableName = entity.tableName();
        cols.append("id");
        vals.append(getBindingField("id"));
        Collection<String> exclude = entity.exclude();
        entity.forEach((field, value) -> {
            if (!"id".equals(field) && (null == exclude || !exclude.contains(field))) {
                cols.append(",").append(" ").append(fieldToColumn(field));
                vals.append(",").append(" ");
                if (null == value) {
                    vals.append("null");
                } else {
                    vals.append(getBindingField(field));
                }

            }
        });
        return toInsertSql(tableName, cols, vals);
    }

    private static String dynamicUpdateSql(BaseDynamicEntity<?> entity) {
        StringBuilder cols = new StringBuilder();
        String tableName = entity.tableName();
        Collection<String> exclude = entity.exclude();
        entity.forEach((field, value) -> {
            if (!"id".equals(field) && (null == exclude || !exclude.contains(field))) {
                cols.append(" ").append(fieldToColumn(field)).append("=");
                if (null == value) {
                    cols.append("null");
                } else {
                    cols.append(getBindingField(field));
                }

                cols.append(",");
            }
        });
        if (0 == cols.length()) {
            throw new FrameworkException("未设置动态表要更新的数据");
        } else {
            cols.setLength(cols.length() - 1);
            return toUpdateSql(tableName, cols);
        }
    }

    private static String dynamicDeleteSql(BaseDynamicEntity<?> entity) {
        String tableName = entity.tableName();
        return toDeleteSql(tableName);
    }

    private static String getBindingField(String field) {
        return PersistentUtil.getBindingField(field);
    }

    private static String entityInsertSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        cols.append("id");
        vals.append(getBindingField("id"));
        String tableName = PersistentUtil.getTableName(clazz);
        List<String> persistentFields = PersistentUtil.getPersistentFields(clazz);
        persistentFields.forEach((field) -> {
            if (!"id".equals(field)) {
                cols.append(",").append(" ").append(fieldToColumn(field));
                vals.append(",").append(" ").append(getBindingField(field));
            }
        });
        return toInsertSql(tableName, cols, vals);
    }

    private static String entityUpdateSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        StringBuilder cols = new StringBuilder();
        String tableName = PersistentUtil.getTableName(clazz);
        List<String> persistentFields = PersistentUtil.getUpdatePersistentFields(clazz);
        persistentFields.forEach((field) -> {
            if (!"id".equals(field)) {
                String col = fieldToColumn(field);
                cols.append(" ").append(col).append("=").append(getBindingField(field)).append(",");
            }
        });
        cols.setLength(cols.length() - 1);
        return toUpdateSql(tableName, cols);
    }

    private static String entityDeleteSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        String tableName = PersistentUtil.getTableName(clazz);
        return toDeleteSql(tableName);
    }

    private static String toInsertSql(String tableName, CharSequence cols, CharSequence vals) {
        return "INSERT INTO " + tableName + " " + "(" + cols + ")" + " VALUES " + "(" + vals + ")";
    }

    private static String toUpdateSql(String tableName, CharSequence cols) {
        return "UPDATE " + tableName + " SET " + cols + " WHERE " + "id" + "=" + getBindingField("id");
    }

    private static String toDeleteSql(String tableName) {
        return "DELETE FROM " + tableName + " WHERE " + "id" + "=" + getBindingField("id");
    }

    private static String fieldToColumn(String field) {
        return PersistentUtil.fieldToColumn(field);
    }
}
