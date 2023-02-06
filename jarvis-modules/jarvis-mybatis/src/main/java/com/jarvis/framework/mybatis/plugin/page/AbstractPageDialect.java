package com.jarvis.framework.mybatis.plugin.page;

import com.jarvis.framework.search.Page;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年3月18日
 */
public abstract class AbstractPageDialect implements PageDialect {

    protected final String startNumberParameter = "page::startNumber";

    protected final String endNumberParameter = "page::endNumber";

    protected final String limitNumberParameter = "page::pageSize";

    /**
     *
     * @see com.jarvis.framework.mybatis.plugin.page.PageDialect#skip(org.apache.ibatis.mapping.MappedStatement, java.lang.Object,
     *      org.apache.ibatis.session.RowBounds)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean skip(MappedStatement ms, Object parameter, RowBounds rowBounds) {
        if (null == parameter) {
            return true;
        }
        /*final String id = ms.getId();
        if (id.endsWith(".page")) {
            return true;
        }*/
        if (parameter instanceof Map) {
            final Map<String, Object> parameterMap = (Map<String, Object>) parameter;
            return !parameterMap.values().stream().anyMatch(val -> {
                return (val instanceof Page);
            });
        }
        return true;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.plugin.page.PageDialect#processPageParameter(org.apache.ibatis.mapping.MappedStatement, java.lang.Object,
     *      org.apache.ibatis.mapping.BoundSql, org.apache.ibatis.cache.CacheKey)
     */
    @Override
    public Object processPageParameter(MappedStatement ms, Object parameter, BoundSql boundSql,
                                       CacheKey cacheKey) {
        final List<ParameterMapping> parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        final String pageKey = getPageKey(parameter, cacheKey);
        parameterMappings.add(createParameterMapping(ms.getConfiguration(), getLimitNumberParameter(pageKey)));
        parameterMappings.add(createParameterMapping(ms.getConfiguration(), getStartNumberParameter(pageKey)));
        return parameterMappings;
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.plugin.page.PageDialect#getPageSql(org.apache.ibatis.mapping.MappedStatement,
     *      org.apache.ibatis.mapping.BoundSql, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.cache.CacheKey)
     */
    @Override
    public String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds,
                             CacheKey pageKey) {
        String sql = boundSql.getSql();

        sql = sql + " LIMIT ? OFFSET ?";

        return sql;
    }

    protected String getPageKey(Object parameter, CacheKey cacheKey) {
        final Entry<String, Object> pageEntry = getPage(parameter);
        final Page page = (Page) pageEntry.getValue();
        //
        cacheKey.update(page.getPageNumber());
        cacheKey.update(page.getPageSize());

        return getPage(parameter).getKey();
    }

    private Entry<String, Object> getPage(Object parameter) {
        final Map<String, Object> parameterMap = (Map<String, Object>) parameter;
        Entry<String, Object> entry = null;
        for (final Entry<String, Object> e : parameterMap.entrySet()) {
            if (e.getValue() instanceof Page) {
                entry = e;
                break;
            }
        }
        return entry;
    }

    protected String getStartNumberParameter(String pageKey) {
        return pageKey + ".startNumber";
    }

    protected String getEndNumberParameter(String pageKey) {
        return pageKey + ".endNumber";
    }

    protected String getLimitNumberParameter(String pageKey) {
        return pageKey + ".pageSize";
    }

    protected ParameterMapping createParameterMapping(Configuration configuration, String property) {
        return new ParameterMapping.Builder(configuration, property, Integer.class).build();
    }

}
