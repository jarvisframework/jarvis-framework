package com.jarvis.framework.mybatis.plugin.page.support;

import com.jarvis.framework.mybatis.plugin.page.AbstractPageDialect;
import com.jarvis.framework.mybatis.plugin.page.PageDialect;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年3月18日
 */
public class OraclePageDialect extends AbstractPageDialect {

    public static final PageDialect INSTANCE = new OraclePageDialect();

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
        parameterMappings.add(createParameterMapping(ms.getConfiguration(), getEndNumberParameter(pageKey)));
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

        sql = "SELECT a.* FROM ("
                + "SELECT  rownum as rn_, temp.* FROM ("
                + sql
                + ") temp WHERE rownum <= ?"
                + ") a WHERE a.rn_ > ? ORDER BY a.rn_";

        return sql;
    }

}
