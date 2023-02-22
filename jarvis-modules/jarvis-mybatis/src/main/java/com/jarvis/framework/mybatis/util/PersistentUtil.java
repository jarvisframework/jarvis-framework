package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.annotation.IgnoreUpdate;
import com.jarvis.framework.constant.BaseFieldConstant;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.core.entity.BaseSimpleEntity;
import com.jarvis.framework.core.entity.LongIdDynamicEntity;
import com.jarvis.framework.core.entity.LongIdSimpleEntity;
import com.jarvis.framework.core.entity.StringIdDynamicEntity;
import com.jarvis.framework.core.entity.StringIdSimpleEntity;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.util.ColumnFunctionUtil;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月15日
 */
public class PersistentUtil {

    /**
     * 设置id主键值
     *
     * @param <Id> ID类型
     * @param entity 实体对象
     */
    @SuppressWarnings("rawtypes")
    public static <Id extends Serializable> void setEntityIdPrimaryKey(BaseIdPrimaryKeyEntity<Id> entity) {
        if (null != entity.getId()) {
            return;
        }
        final Class<? extends BaseIdPrimaryKeyEntity> clazz = entity.getClass();
        if (StringIdSimpleEntity.class.isAssignableFrom(clazz)) {
            ((StringIdSimpleEntity) entity).setId(UuidGeneratorUtil.getInstance().nextId());
            return;
        }
        if (StringIdDynamicEntity.class.isAssignableFrom(clazz)) {
            ((StringIdDynamicEntity) entity).setId(UuidGeneratorUtil.getInstance().nextId());
            return;
        }
        if (LongIdSimpleEntity.class.isAssignableFrom(clazz)) {
            ((LongIdSimpleEntity) entity).setId(SnowflakeIdGeneratorUtil.getInstance().nextId());
            return;
        }
        if (LongIdDynamicEntity.class.isAssignableFrom(clazz)) {
            ((LongIdDynamicEntity) entity).setId(SnowflakeIdGeneratorUtil.getInstance().nextId());
            return;
        }
    }

    public static String getTableName(Class<? extends BaseIdPrimaryKeyEntity<?>> clazz) {
        final Table annotation = clazz.getAnnotation(Table.class);
        if (null == annotation) {
            if (BaseSimpleEntity.class.isAssignableFrom(clazz)) {
                throw new FrameworkException("实体类上未标注@Table注解");
            }
            return null;
        }
        return annotation.name();
    }

    @SuppressWarnings("unchecked")
    public static String getTableName(BaseIdPrimaryKeyEntity<?> entity) {
        final Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = (Class<? extends BaseIdPrimaryKeyEntity<?>>) entity
                .getClass();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            return ((BaseDynamicEntity<?>) entity).tableName();
        }
        return getTableName(clazz);
    }

    public static String getTableName(ProviderContext context) {
        final Class<? extends BaseIdPrimaryKeyEntity<?>> entityClazz = getMapperEntityClazz(context);
        return getTableName(entityClazz);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Class<? extends BaseIdPrimaryKeyEntity<?>> getMapperEntityClazz(ProviderContext context) {
        final Class<?> mapperType = context.getMapperType();
        final Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(mapperType);
        if (null == typeVariableMap || typeVariableMap.isEmpty()) {
            throw new FrameworkException("Mapper[" + mapperType.getName() + "]类不是继承BaseMapper");
        }
        TypeVariable typeVariable;
        for (final Entry<TypeVariable, Type> entry : typeVariableMap.entrySet()) {
            typeVariable = entry.getKey();
            if ("Entity".equals(typeVariable.getName())) {
                return (Class<? extends BaseIdPrimaryKeyEntity<?>>) entry.getValue();
            }
        }
        throw new FrameworkException("未找到Mapper[" + mapperType.getName() + "]类对应的实体类");
    }

    private static List<String> getNotPersistentFields(Class<?> clazz) {
        final List<String> list = new ArrayList<>();
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            final Field[] fields = searchType.getDeclaredFields();
            for (final Field field : fields) {
                if (field.isAnnotationPresent(Transient.class)) {
                    list.add(field.getName());
                }
            }
            searchType = searchType.getSuperclass();
        }
        return list;
    }

    public static List<String> getPersistentFields(Class<?> clazz) {
        final List<String> list = new ArrayList<String>(32);

        final List<String> notPersistentFields = getNotPersistentFields(clazz);
        final Method[] methods = getGetterMethods(clazz);
        String field;
        for (final Method method : methods) {
            if (method.isAnnotationPresent(Transient.class) || method.isBridge()) {
                continue;
            }
            /*if (method.getParameters().length > 0) {
                continue;
            }
            if (method.getName().equals("getClass")) {
                continue;
            }
            if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                continue;
            }*/
            field = toField(method);
            if (notPersistentFields.contains(field)) {
                continue;
            }
            list.add(field);
        }

        return list;
    }

    private static List<String> getNotUpdatePersistentFields(Class<?> clazz) {
        final List<String> list = new ArrayList<>();
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && !HashMap.class.equals(searchType) && searchType != null) {
            final Field[] fields = searchType.getDeclaredFields();
            for (final Field field : fields) {
                if (field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(IgnoreUpdate.class)) {
                    list.add(field.getName());
                }
            }
            searchType = searchType.getSuperclass();
        }
        return list;
    }

    public static List<String> getUpdatePersistentFields(Class<?> clazz) {
        final List<String> list = new ArrayList<String>(32);

        final List<String> notPersistentFields = getNotUpdatePersistentFields(clazz);
        final Method[] methods = getGetterMethods(clazz);
        String field;
        for (final Method method : methods) {
            if (method.isAnnotationPresent(Transient.class) || method.isAnnotationPresent(IgnoreUpdate.class)
                    || method.isBridge()) {
                continue;
            }
            /*if (method.getParameters().length > 0) {
                continue;
            }
            if (method.getName().equals("getClass")) {
                continue;
            }
            if (!method.getName().startsWith("get") && !method.getName().startsWith("is")) {
                continue;
            }*/
            field = toField(method);
            if (notPersistentFields.contains(field)) {
                continue;
            }
            list.add(field);
        }

        return list;
    }

    public static Collection<String> getPersistentFields(BaseIdPrimaryKeyEntity<?> entity) {
        final Class<?> clazz = entity.getClass();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            final Collection<String> keySet = new LinkedHashSet<String>(16);
            keySet.add(BaseFieldConstant.ID);
            keySet.addAll(((BaseDynamicEntity<?>) entity).keySet());
            return keySet;
        }
        return getPersistentFields(clazz);
    }

    /**
     * Java属性转字段：showName => show_name
     *
     * @param field Java属性
     * @return 字段名称
     */
    public static String fieldToColumn(String field) {
        /*final StringBuilder col = new StringBuilder(32);
        final char[] arrayChar = field.toCharArray();
        for (final char c : arrayChar) {
            if (Character.isUpperCase(c)) {
                col.append(SymbolConstant.UNDER_LINE).append(Character.toLowerCase(c));
            } else {
                col.append(c);
            }
        }

        return col.toString();*/
        return ColumnFunctionUtil.fieldToColumn(field);
    }

    /**
     * 字段转Java属性：SHOW_NAME => showName
     *
     * @param column 字段名称
     * @return 属性名称
     */
    public static String columnToField(String column) {
        final StringBuilder str = new StringBuilder(32);
        final char[] arrayChar = column.toCharArray();
        boolean isUpperCase = false;
        for (final char c : arrayChar) {
            if (c == '_') {
                isUpperCase = true;
            } else {
                if (isUpperCase) {
                    str.append(Character.toUpperCase(c));
                    isUpperCase = false;
                } else {
                    str.append(Character.toLowerCase(c));
                }
            }
        }

        return str.toString();
    }

    public static String getBindingField(String field) {
        return SymbolConstant.HASH_OPEN_BRACE + field + SymbolConstant.CLOSE_BRACE;
    }

    private static String toField(Method method) {
        String name = method.getName();
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            name = name.substring(3);
        }
        if (1 == name.length()) {
            return name.toLowerCase();
        }
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private static Method[] getGetterMethods(Class<?> clazz) {
        return ReflectionUtils.getUniqueDeclaredMethods(
                clazz,
                (m) -> {
                    final Class<?> dc = m.getDeclaringClass();
                    final Class<?> rc = m.getReturnType();
                    final int parameterCount = m.getParameterCount();
                    final String name = m.getName();
                    final boolean isGetter = name.startsWith("get") || name.startsWith("is");
                    return Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
                            && 0 == parameterCount && isGetter
                            && !void.class.equals(rc)
                            && !HashMap.class.equals(dc) && !AbstractMap.class.equals(dc) && !Map.class.equals(dc)
                            && !Object.class.equals(dc);
                });
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        System.out.println(columnToField("SHOW__AME"));
    }
}
