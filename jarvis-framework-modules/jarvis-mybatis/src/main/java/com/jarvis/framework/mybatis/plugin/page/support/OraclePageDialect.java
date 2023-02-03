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

public class OraclePageDialect extends AbstractPageDialect {
    public static final PageDialect INSTANCE = new OraclePageDialect();

    public OraclePageDialect() {
    }

    public Object processPageParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey cacheKey) {
        List<ParameterMapping> parameterMappings = new ArrayList(boundSql.getParameterMappings());
        String pageKey = this.getPageKey(parameter, cacheKey);
        parameterMappings.add(this.createParameterMapping(ms.getConfiguration(), this.getEndNumberParameter(pageKey)));
        parameterMappings.add(this.createParameterMapping(ms.getConfiguration(), this.getStartNumberParameter(pageKey)));
        return parameterMappings;
    }

    public String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey pageKey) {
        String sql = boundSql.getSql();
        sql = "SELECT a.* FROM (SELECT  rownum as rn_, temp.* FROM (" + sql + ") temp WHERE rownum <= ?) a WHERE a.rn_ > ? ORDER BY a.rn_";
        return sql;
    }
}
