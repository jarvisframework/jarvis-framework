package com.jarvis.framework.mybatis.plugin.page;

import com.jarvis.framework.search.Page;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractPageDialect implements PageDialect {

    protected final String startNumberParameter = "page::startNumber";

    protected final String endNumberParameter = "page::endNumber";

    protected final String limitNumberParameter = "page::pageSize";

    public AbstractPageDialect() {
    }

    public boolean skip(MappedStatement ms, Object parameter, RowBounds rowBounds) {
        if (null == parameter) {
            return true;
        } else if (parameter instanceof Map) {
            Map<String, Object> parameterMap = (Map)parameter;
            return !parameterMap.values().stream().anyMatch((val) -> {
                return val instanceof Page;
            });
        } else {
            return true;
        }
    }

    public Object processPageParameter(MappedStatement ms, Object parameter, BoundSql boundSql, CacheKey cacheKey) {
        List<ParameterMapping> parameterMappings = new ArrayList(boundSql.getParameterMappings());
        String pageKey = this.getPageKey(parameter, cacheKey);
        parameterMappings.add(this.createParameterMapping(ms.getConfiguration(), this.getLimitNumberParameter(pageKey)));
        parameterMappings.add(this.createParameterMapping(ms.getConfiguration(), this.getStartNumberParameter(pageKey)));
        return parameterMappings;
    }

    public String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey pageKey) {
        String sql = boundSql.getSql();
        sql = sql + " LIMIT ? OFFSET ?";
        return sql;
    }

    protected String getPageKey(Object parameter, CacheKey cacheKey) {
        Entry<String, Object> pageEntry = this.getPage(parameter);
        Page page = (Page)pageEntry.getValue();
        cacheKey.update(page.getPageNumber());
        cacheKey.update(page.getPageSize());
        return (String)this.getPage(parameter).getKey();
    }

    private Entry<String, Object> getPage(Object parameter) {
        Map<String, Object> parameterMap = (Map)parameter;
        Entry<String, Object> entry = null;
        Iterator var4 = parameterMap.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, Object> e = (Entry)var4.next();
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
        return (new Builder(configuration, property, Integer.class)).build();
    }
}
