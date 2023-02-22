package com.jarvis.framework.mybatis.plugin.query;

import com.jarvis.framework.constant.SymbolConstant;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年4月27日
 */
public abstract class ObjectQueryUtil {

    private static final Map<String, MappedStatement> OBJECT_MS = new ConcurrentHashMap<String, MappedStatement>();

    public static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    /**
     * 是否支持
     *
     * @param ms
     * @return
     */
    public static boolean isObjectQuery(MappedStatement ms) {
        final String id = ms.getId();
        return (id.endsWith(".getObjectBy") || id.endsWith(".queryObjectsBy") || id.endsWith(".queryByMultiple")
                || id.endsWith(".getByMultiple"));
    }

    /**
     * 查询
     *
     * @param executor
     * @param ms
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param boundSql
     * @param cacheKey
     * @return
     * @throws SQLException
     */
    public static Object doObjectQuery(Executor executor,
                                       MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql,
                                       CacheKey cacheKey) throws SQLException {

        final Class<?> clazz = ObjectQueryUtil.getResultType(parameter);

        final MappedStatement multipleMs = ObjectQueryUtil.objectMappedStatement(ms, clazz);
        final Object result = executor.query(multipleMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey,
                boundSql);

        return result;
    }

    /**
     * count查询的MappedStatement
     *
     * @param ms
     * @param newMsId
     * @return
     */
    private static MappedStatement objectMappedStatement(MappedStatement ms, Class<?> clazz) {
        final String msKey = objectMsKey(ms, clazz);
        if (OBJECT_MS.containsKey(msKey)) {
            return OBJECT_MS.get(msKey);
        }
        final MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            final StringBuilder keyProperties = new StringBuilder();
            for (final String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        // 自定义返回值
        final List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        final ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), clazz,
                EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        final MappedStatement countMs = builder.build();
        OBJECT_MS.putIfAbsent(msKey, countMs);

        return countMs;
    }

    /**
     * @param ms
     * @param clazz
     * @return
     */
    private static String objectMsKey(MappedStatement ms, Class<?> clazz) {
        return ms.getId() + SymbolConstant.COLON + clazz.getName();
    }

    /**
     * 获取Class参数
     *
     * @param parameter
     * @return
     */
    private static Class<?> getResultType(Object parameter) {
        if (!(parameter instanceof Map)) {
            return null;
        }
        final Map<String, Object> parameterMap = (Map<String, Object>) parameter;
        for (final Object val : parameterMap.values()) {
            if (!(val instanceof Class<?>)) {
                continue;
            }
            return (Class<?>) val;
        }

        return null;
    }
}
