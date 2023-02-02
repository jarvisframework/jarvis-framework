package com.jarvis.framework.mybatis.util;

import com.jarvis.framework.annotation.IgnoreUpdate;
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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PersistentUtil {

    public static <Id extends Serializable> void setEntityIdPrimaryKey(BaseIdPrimaryKeyEntity<Id> entity) {
        if (null == entity.getId()) {
            Class<? extends BaseIdPrimaryKeyEntity> clazz = entity.getClass();
            if (StringIdSimpleEntity.class.isAssignableFrom(clazz)) {
                ((StringIdSimpleEntity) entity).setId(UuidGeneratorUtil.getInstance().nextId());
            } else if (StringIdDynamicEntity.class.isAssignableFrom(clazz)) {
                ((StringIdDynamicEntity) entity).setId(UuidGeneratorUtil.getInstance().nextId());
            } else if (LongIdSimpleEntity.class.isAssignableFrom(clazz)) {
                ((LongIdSimpleEntity) entity).setId(SnowflakeIdGeneratorUtil.getInstance().nextId());
            } else if (LongIdDynamicEntity.class.isAssignableFrom(clazz)) {
                ((LongIdDynamicEntity) entity).setId(SnowflakeIdGeneratorUtil.getInstance().nextId());
            }
        }
    }

    public static String getTableName(Class<? extends BaseIdPrimaryKeyEntity<?>> clazz) {
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        if (null == annotation) {
            if (BaseSimpleEntity.class.isAssignableFrom(clazz)) {
                throw new FrameworkException("实体类上未标注@Table注解");
            } else {
                return null;
            }
        } else {
            return annotation.name();
        }
    }

    public static String getTableName(BaseIdPrimaryKeyEntity<?> entity) {
        Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = entity.getClass();
        return BaseDynamicEntity.class.isAssignableFrom(clazz) ? ((BaseDynamicEntity) entity).tableName() : getTableName(clazz);
    }

    public static String getTableName(ProviderContext context) {
        Class<? extends BaseIdPrimaryKeyEntity<?>> entityClazz = getMapperEntityClazz(context);
        return getTableName(entityClazz);
    }

    public static Class<? extends BaseIdPrimaryKeyEntity<?>> getMapperEntityClazz(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();
        Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(mapperType);
        if (null != typeVariableMap && !typeVariableMap.isEmpty()) {
            Iterator var4 = typeVariableMap.entrySet().iterator();

            TypeVariable typeVariable;
            Entry entry;
            do {
                if (!var4.hasNext()) {
                    throw new FrameworkException("未找到Mapper[" + mapperType.getName() + "]类对应的实体类");
                }

                entry = (Entry) var4.next();
                typeVariable = (TypeVariable) entry.getKey();
            } while (!"Entity".equals(typeVariable.getName()));

            return (Class) entry.getValue();
        } else {
            throw new FrameworkException("Mapper[" + mapperType.getName() + "]类不是继承BaseMapper");
        }
    }

    private static List<String> getNotPersistentFields(Class<?> clazz) {
        List<String> list = new ArrayList();

        for (Class searchType = clazz; !Object.class.equals(searchType) && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = searchType.getDeclaredFields();
            Field[] var4 = fields;
            int var5 = fields.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                if (field.isAnnotationPresent(Transient.class)) {
                    list.add(field.getName());
                }
            }
        }

        return list;
    }

    public static List<String> getPersistentFields(Class<?> clazz) {
        List<String> list = new ArrayList(32);
        List<String> notPersistentFields = getNotPersistentFields(clazz);
        Method[] methods = getGetterMethods(clazz);
        Method[] var5 = methods;
        int var6 = methods.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (!method.isAnnotationPresent(Transient.class) && !method.isBridge()) {
                String field = toField(method);
                if (!notPersistentFields.contains(field)) {
                    list.add(field);
                }
            }
        }

        return list;
    }

    private static List<String> getNotUpdatePersistentFields(Class<?> clazz) {
        List<String> list = new ArrayList();

        for (Class searchType = clazz; !Object.class.equals(searchType) && !HashMap.class.equals(searchType) && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = searchType.getDeclaredFields();
            Field[] var4 = fields;
            int var5 = fields.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                if (field.isAnnotationPresent(Transient.class) || field.isAnnotationPresent(IgnoreUpdate.class)) {
                    list.add(field.getName());
                }
            }
        }

        return list;
    }

    public static List<String> getUpdatePersistentFields(Class<?> clazz) {
        List<String> list = new ArrayList(32);
        List<String> notPersistentFields = getNotUpdatePersistentFields(clazz);
        Method[] methods = getGetterMethods(clazz);
        Method[] var5 = methods;
        int var6 = methods.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (!method.isAnnotationPresent(Transient.class) && !method.isAnnotationPresent(IgnoreUpdate.class) && !method.isBridge()) {
                String field = toField(method);
                if (!notPersistentFields.contains(field)) {
                    list.add(field);
                }
            }
        }

        return list;
    }

    public static Collection<String> getPersistentFields(BaseIdPrimaryKeyEntity<?> entity) {
        Class<?> clazz = entity.getClass();
        if (BaseDynamicEntity.class.isAssignableFrom(clazz)) {
            Collection<String> keySet = new LinkedHashSet(16);
            keySet.add("id");
            keySet.addAll(((BaseDynamicEntity) entity).keySet());
            return keySet;
        } else {
            return getPersistentFields(clazz);
        }
    }

    public static String fieldToColumn(String field) {
        return ColumnFunctionUtil.fieldToColumn(field);
    }

    public static String columnToField(String column) {
        StringBuilder str = new StringBuilder(32);
        char[] arrayChar = column.toCharArray();
        boolean isUpperCase = false;
        char[] var4 = arrayChar;
        int var5 = arrayChar.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            char c = var4[var6];
            if (c == '_') {
                isUpperCase = true;
            } else if (isUpperCase) {
                str.append(Character.toUpperCase(c));
                isUpperCase = false;
            } else {
                str.append(Character.toLowerCase(c));
            }
        }

        return str.toString();
    }

    public static String getBindingField(String field) {
        return "#{" + field + "}";
    }

    private static String toField(Method method) {
        String name = method.getName();
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            name = name.substring(3);
        }

        return 1 == name.length() ? name.toLowerCase() : Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private static Method[] getGetterMethods(Class<?> clazz) {
        return ReflectionUtils.getUniqueDeclaredMethods(clazz, (m) -> {
            Class<?> dc = m.getDeclaringClass();
            Class<?> rc = m.getReturnType();
            int parameterCount = m.getParameterCount();
            String name = m.getName();
            boolean isGetter = name.startsWith("get") || name.startsWith("is");
            return Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && 0 == parameterCount && isGetter && !Void.TYPE.equals(rc) && !HashMap.class.equals(dc) && !AbstractMap.class.equals(dc) && !Map.class.equals(dc) && !Object.class.equals(dc);
        });
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        System.out.println(columnToField("SHOW__AME"));
    }
}
