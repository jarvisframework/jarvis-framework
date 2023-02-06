package com.jarvis.framework.mybatis.plugin.page;

import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年12月28日
 */
public abstract class PageQueryUtil {

    public static final String COUNT_MS_SUFFIX = "CountAuto";

    private static final Map<String, MappedStatement> COUNT_MS = new ConcurrentHashMap<>();

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    private static final Field additionalParametersField;

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (final NoSuchFieldException e) {
            throw new FrameworkException("获取[BoundSql]属性[additionalParameters]出错！", e);
        }
    }

    /**
     * 获取 BoundSql 属性值 additionalParameters
     *
     * @param boundSql
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAdditionalParameter(BoundSql boundSql) {
        try {
            return (Map<String, Object>) additionalParametersField.get(boundSql);
        } catch (final IllegalAccessException e) {
            throw new FrameworkException("获取[BoundSql]属性值[additionalParameters]出错！", e);
        }
    }

    /**
     * 创建分页 BoundSql
     *
     * @param ms
     * @param boundSql
     * @param pageSql
     * @param parameterMappings
     * @param parameter
     * @return
     */
    public static BoundSql pageBoundSql(MappedStatement ms, BoundSql boundSql, String pageSql,
                                        List<ParameterMapping> parameterMappings, Object parameter) {
        final Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        final BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, parameterMappings, parameter);
        additionalParameters.forEach((key, val) -> pageBoundSql.setAdditionalParameter(key, val));
        return pageBoundSql;
    }

    /**
     * 获取Page参数
     *
     * @param parameter
     * @return
     */
    public static Page getPage(Object parameter) {
        if (!(parameter instanceof Map)) {
            return null;
        }
        final Map<String, Object> parameterMap = (Map<String, Object>) parameter;
        for (final Object val : parameterMap.values()) {
            if (!(val instanceof Page)) {
                continue;
            }
            return (Page) val;
        }

        return null;
    }

    /**
     * count查询的MappedStatement
     *
     * @param ms
     * @param newMsId
     * @return
     */
    public static MappedStatement countMappedStatement(MappedStatement ms) {
        final String newMsId = getCountMsId(ms);
        if (COUNT_MS.containsKey(newMsId)) {
            return COUNT_MS.get(newMsId);
        }
        final MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newMsId,
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
        //count查询返回值int
        final List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        final ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class,
                EMPTY_RESULTMAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        final MappedStatement countMs = builder.build();
        COUNT_MS.putIfAbsent(newMsId, countMs);

        return countMs;
    }

    private static String getCountMsId(MappedStatement pageMs) {
        return pageMs.getId() + COUNT_MS_SUFFIX;
    }

    /**
     * 创建总数 BoundSql
     *
     * @param ms
     * @param boundSql
     * @param countSql
     * @param parameterMappings
     * @param parameter
     * @return
     */
    public static BoundSql countBoundSql(MappedStatement ms, BoundSql boundSql, String countSql,
                                         List<ParameterMapping> parameterMappings, Object parameter) {
        final Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        final BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, parameterMappings, parameter);
        additionalParameters.forEach((key, val) -> countBoundSql.setAdditionalParameter(key, val));
        return countBoundSql;
    }
}
