package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.constant.BaseColumnConstant;
import com.jarvis.framework.constant.BaseFieldConstant;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月14日
 */
public class MapperEntityUtil {

    private static final Map<Class<?>, String> ENTITY_INSERT_SQL_CACHE = new ConcurrentHashMap<>(16);

    private static final Map<Class<?>, String> ENTITY_UPDATE_SQL_CACHE = new ConcurrentHashMap<>(16);

    private static final Map<Class<?>, String> ENTITY_DELETE_SQL_CACHE = new ConcurrentHashMap<>(16);

    //private static final Map<Class<?>, List<String>> ENTITY_COLUMN_CACHE = new ConcurrentHashMap<>(16);

    @SuppressWarnings("unchecked")
    public static String mapperEntity2InsertSql(BaseIdPrimaryKeyEntity<?> entity) {
        final Class<?> clazz = entity.getClass();
        if (null == entity.getId()) {
            PersistentUtil.setEntityIdPrimaryKey(entity);
        }
        EntityAutoFillingHolder.insert(entity);
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicInsertSql((BaseDynamicEntity<?>) entity);
        }
        String sql = ENTITY_INSERT_SQL_CACHE.get(clazz);
        if (null != sql) {
            return sql;
        }
        sql = entityInsertSql((Class<? extends BaseSimpleEntity<?>>) clazz);

        ENTITY_INSERT_SQL_CACHE.putIfAbsent(clazz, sql);

        return sql;
    }

    @SuppressWarnings("unchecked")
    public static String mapperEntity2UpdateSql(BaseIdPrimaryKeyEntity<?> entity) {
        final Class<?> clazz = entity.getClass();
        EntityAutoFillingHolder.update(entity);
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicUpdateSql((BaseDynamicEntity<?>) entity);
        }
        String sql = ENTITY_UPDATE_SQL_CACHE.get(clazz);
        if (null != sql) {
            return sql;
        }
        sql = entityUpdateSql((Class<? extends BaseSimpleEntity<?>>) clazz);

        ENTITY_UPDATE_SQL_CACHE.putIfAbsent(clazz, sql);

        return sql;
    }

    @SuppressWarnings("unchecked")
    public static String mapperEntity2DeleteSql(BaseIdPrimaryKeyEntity<?> entity) {
        final Class<?> clazz = entity.getClass();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return dynamicDeleteSql((BaseDynamicEntity<?>) entity);
        }
        String sql = ENTITY_DELETE_SQL_CACHE.get(clazz);
        if (null != sql) {
            return sql;
        }
        sql = entityDeleteSql((Class<? extends BaseSimpleEntity<?>>) clazz);

        ENTITY_DELETE_SQL_CACHE.putIfAbsent(clazz, sql);

        return sql;
    }

    private static String dynamicInsertSql(BaseDynamicEntity<?> entity) {
        final StringBuilder cols = new StringBuilder();
        final StringBuilder vals = new StringBuilder();
        final String tableName = entity.tableName();
        cols.append(BaseColumnConstant.ID);
        vals.append(getBindingField(BaseFieldConstant.ID));
        final Collection<String> exclude = entity.exclude();
        entity.forEach((field, value) -> {
            if (BaseFieldConstant.ID.equals(field) || (null != exclude && exclude.contains(field))) {
                return;
            }
            cols.append(SymbolConstant.COMMA).append(SymbolConstant.SPACE).append(fieldToColumn(field));
            vals.append(SymbolConstant.COMMA).append(SymbolConstant.SPACE);
            if (null == value) {
                vals.append("null");
            } else {
                vals.append(getBindingField(field));
            }
        });
        return toInsertSql(tableName, cols, vals);
    }

    private static String dynamicUpdateSql(BaseDynamicEntity<?> entity) {
        final StringBuilder cols = new StringBuilder();
        final String tableName = entity.tableName();
        final Collection<String> exclude = entity.exclude();
        entity.forEach((field, value) -> {
            if (BaseFieldConstant.ID.equals(field) || (null != exclude && exclude.contains(field))) {
                return;
            }
            cols.append(SymbolConstant.SPACE).append(fieldToColumn(field)).append("=");
            if (null == value) {
                cols.append("null");
            } else {
                cols.append(getBindingField(field));
            }
            cols.append(SymbolConstant.COMMA);
        });

        if (0 == cols.length()) {
            throw new FrameworkException("未设置动态表要更新的数据");
        }

        cols.setLength(cols.length() - 1);

        return toUpdateSql(tableName, cols);
    }

    private static String dynamicDeleteSql(BaseDynamicEntity<?> entity) {
        final String tableName = entity.tableName();
        return toDeleteSql(tableName);
    }

    private static String getBindingField(String field) {
        return PersistentUtil.getBindingField(field);
    }

    private static String entityInsertSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        final StringBuilder cols = new StringBuilder();
        final StringBuilder vals = new StringBuilder();
        cols.append(BaseColumnConstant.ID);
        vals.append(getBindingField(BaseFieldConstant.ID));

        final String tableName = PersistentUtil.getTableName(clazz);
        final List<String> persistentFields = PersistentUtil.getPersistentFields(clazz);

        persistentFields.forEach((field) -> {
            if (BaseFieldConstant.ID.equals(field)) {
                return;
            }
            cols.append(SymbolConstant.COMMA).append(SymbolConstant.SPACE).append(fieldToColumn(field));
            vals.append(SymbolConstant.COMMA).append(SymbolConstant.SPACE).append(getBindingField(field));
        });

        return toInsertSql(tableName, cols, vals);
    }

    /**
     * 类中所有属性对应的字段
     *
     * @param clazz 类名
     * @return List
     */
    /*public static List<String> getPersistentColumns(Class<? extends BaseSimpleEntity<?>> clazz) {
        if (ENTITY_COLUMN_CACHE.containsKey(clazz)) {
            return ENTITY_COLUMN_CACHE.get(clazz);
        }
        final List<String> columns = PersistentUtil.getPersistentFields(clazz)
            .stream().map(e -> fieldToColumn(e)).collect(Collectors.toList());
        ENTITY_COLUMN_CACHE.putIfAbsent(clazz, columns);
        return columns;
    }*/

    private static String entityUpdateSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        final StringBuilder cols = new StringBuilder();

        final String tableName = PersistentUtil.getTableName(clazz);
        final List<String> persistentFields = PersistentUtil.getUpdatePersistentFields(clazz);

        persistentFields.forEach((field) -> {
            if (BaseFieldConstant.ID.equals(field)) {
                return;
            }
            final String col = fieldToColumn(field);
            cols.append(SymbolConstant.SPACE)
                    .append(col).append("=").append(getBindingField(field))
                    .append(SymbolConstant.COMMA);
        });

        cols.setLength(cols.length() - 1);

        return toUpdateSql(tableName, cols);
    }

    private static String entityDeleteSql(Class<? extends BaseSimpleEntity<?>> clazz) {
        final String tableName = PersistentUtil.getTableName(clazz);
        return toDeleteSql(tableName);

    }

    private static String toInsertSql(String tableName, CharSequence cols, CharSequence vals) {
        return "INSERT INTO " + tableName + " "
                + SymbolConstant.OPEN_PARENTHESIS + cols + SymbolConstant.CLOSE_PARENTHESIS
                + " VALUES "
                + SymbolConstant.OPEN_PARENTHESIS + vals + SymbolConstant.CLOSE_PARENTHESIS;
    }

    private static String toUpdateSql(String tableName, CharSequence cols) {
        return "UPDATE " + tableName + " SET "
                + cols
                + " WHERE " + BaseColumnConstant.ID + "=" + getBindingField(BaseFieldConstant.ID);
    }

    private static String toDeleteSql(String tableName) {
        return "DELETE FROM " + tableName
                + " WHERE "
                + BaseColumnConstant.ID + "=" + getBindingField(BaseFieldConstant.ID);
    }

    private static String fieldToColumn(String field) {
        return PersistentUtil.fieldToColumn(field);
    }
}
