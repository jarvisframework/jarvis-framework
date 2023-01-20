package com.jarvis.framework.mybatis.plugin.query;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ObjectQueryUtil {
    private static final Map<String, MappedStatement> OBJECT_MS = new ConcurrentHashMap();
    public static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList(0);

    public ObjectQueryUtil() {
    }

    public static boolean isObjectQuery(MappedStatement ms) {
        String id = ms.getId();
        return id.endsWith(".getObjectBy") || id.endsWith(".queryObjectsBy") || id.endsWith(".queryByMultiple") || id.endsWith(".getByMultiple");
    }

    public static Object doObjectQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws SQLException {
        Class<?> clazz = getResultType(parameter);
        MappedStatement multipleMs = objectMappedStatement(ms, clazz);
        Object result = executor.query(multipleMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
        return result;
    }

    private static MappedStatement objectMappedStatement(MappedStatement ms, Class<?> clazz) {
        String msKey = objectMsKey(ms, clazz);
        if (OBJECT_MS.containsKey(msKey)) {
            return OBJECT_MS.get(msKey);
        } else {
            Builder builder = new Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType());
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.keyGenerator(ms.getKeyGenerator());
            if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
                StringBuilder keyProperties = new StringBuilder();
                String[] var5 = ms.getKeyProperties();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    String keyProperty = var5[var7];
                    keyProperties.append(keyProperty).append(",");
                }

                keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
                builder.keyProperty(keyProperties.toString());
            }

            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            List<ResultMap> resultMaps = new ArrayList();
            ResultMap resultMap = (new org.apache.ibatis.mapping.ResultMap.Builder(ms.getConfiguration(), ms.getId(), clazz, EMPTY_RESULTMAPPING)).build();
            resultMaps.add(resultMap);
            builder.resultMaps(resultMaps);
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            MappedStatement countMs = builder.build();
            OBJECT_MS.putIfAbsent(msKey, countMs);
            return countMs;
        }
    }

    private static String objectMsKey(MappedStatement ms, Class<?> clazz) {
        return ms.getId() + ":" + clazz.getName();
    }

    private static Class<?> getResultType(Object parameter) {
        if (!(parameter instanceof Map)) {
            return null;
        } else {
            Map<String, Object> parameterMap = (Map) parameter;
            Iterator var2 = parameterMap.values().iterator();

            Object val;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                val = var2.next();
            } while (!(val instanceof Class));

            return (Class) val;
        }
    }
}
