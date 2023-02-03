package com.jarvis.framework.mybatis.plugin.page;

import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PageQueryUtil {
    public static final String COUNT_MS_SUFFIX = "CountAuto";
    private static final Map<String, MappedStatement> COUNT_MS = new ConcurrentHashMap();
    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList(0);
    private static final Field additionalParametersField;

    public PageQueryUtil() {
    }

    public static Map<String, Object> getAdditionalParameter(BoundSql boundSql) {
        try {
            return (Map)additionalParametersField.get(boundSql);
        } catch (IllegalAccessException var2) {
            throw new FrameworkException("获取[BoundSql]属性值[additionalParameters]出错！", var2);
        }
    }

    public static BoundSql pageBoundSql(MappedStatement ms, BoundSql boundSql, String pageSql, List<ParameterMapping> parameterMappings, Object parameter) {
        Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        BoundSql pageBoundSql = new BoundSql(ms.getConfiguration(), pageSql, parameterMappings, parameter);
        additionalParameters.forEach((key, val) -> {
            pageBoundSql.setAdditionalParameter(key, val);
        });
        return pageBoundSql;
    }

    public static Page getPage(Object parameter) {
        if (!(parameter instanceof Map)) {
            return null;
        } else {
            Map<String, Object> parameterMap = (Map)parameter;
            Iterator var2 = parameterMap.values().iterator();

            Object val;
            do {
                if (!var2.hasNext()) {
                    return null;
                }

                val = var2.next();
            } while(!(val instanceof Page));

            return (Page)val;
        }
    }

    public static MappedStatement countMappedStatement(MappedStatement ms) {
        String newMsId = getCountMsId(ms);
        if (COUNT_MS.containsKey(newMsId)) {
            return COUNT_MS.get(newMsId);
        } else {
            Builder builder = new Builder(ms.getConfiguration(), newMsId, ms.getSqlSource(), ms.getSqlCommandType());
            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.keyGenerator(ms.getKeyGenerator());
            if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
                StringBuilder keyProperties = new StringBuilder();
                String[] var4 = ms.getKeyProperties();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String keyProperty = var4[var6];
                    keyProperties.append(keyProperty).append(",");
                }

                keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
                builder.keyProperty(keyProperties.toString());
            }

            builder.timeout(ms.getTimeout());
            builder.parameterMap(ms.getParameterMap());
            List<ResultMap> resultMaps = new ArrayList();
            ResultMap resultMap = (new org.apache.ibatis.mapping.ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, EMPTY_RESULTMAPPING)).build();
            resultMaps.add(resultMap);
            builder.resultMaps(resultMaps);
            builder.resultSetType(ms.getResultSetType());
            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());
            MappedStatement countMs = builder.build();
            COUNT_MS.putIfAbsent(newMsId, countMs);
            return countMs;
        }
    }

    private static String getCountMsId(MappedStatement pageMs) {
        return pageMs.getId() + "CountAuto";
    }

    public static BoundSql countBoundSql(MappedStatement ms, BoundSql boundSql, String countSql, List<ParameterMapping> parameterMappings, Object parameter) {
        Map<String, Object> additionalParameters = getAdditionalParameter(boundSql);
        BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, parameterMappings, parameter);
        additionalParameters.forEach((key, val) -> {
            countBoundSql.setAdditionalParameter(key, val);
        });
        return countBoundSql;
    }

    static {
        try {
            additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
            additionalParametersField.setAccessible(true);
        } catch (NoSuchFieldException var1) {
            throw new FrameworkException("获取[BoundSql]属性[additionalParameters]出错！", var1);
        }
    }
}
