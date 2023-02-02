package com.jarvis.framework.mybatis.plugin;

import com.jarvis.framework.core.exception.BusinessException;
import com.jarvis.framework.mybatis.plugin.page.CountSqlParser;
import com.jarvis.framework.mybatis.plugin.page.PageDialect;
import com.jarvis.framework.mybatis.plugin.page.PageDialectFactory;
import com.jarvis.framework.mybatis.plugin.page.PageQueryUtil;
import com.jarvis.framework.mybatis.plugin.query.ObjectQueryUtil;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Intercepts({@Signature(
    type = Executor.class,
    method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
    type = Executor.class,
    method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
public class PageInterceptor implements Interceptor {
    public PageInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement)args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds)args[2];
        PageDialect dialect = PageDialectFactory.getDialect();
        ResultHandler resultHandler = (ResultHandler)args[3];
        Executor executor = (Executor)invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey)args[4];
            boundSql = (BoundSql)args[5];
        }

        if (ObjectQueryUtil.isObjectQuery(ms)) {
            return ObjectQueryUtil.doObjectQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
        } else if (dialect.skip(ms, parameter, rowBounds)) {
            return invocation.proceed();
        } else {
            Page page = PageQueryUtil.getPage(parameter);
            return this.isDefaultPage(ms) ? this.processDefaultPage(page, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql) : this.processCustomPage(dialect, page, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
        }
    }

    private boolean isDefaultPage(MappedStatement ms) {
        String id = ms.getId();
        return id.endsWith(".page");
    }

    private Object processDefaultPage(Page page, Invocation invocation, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws Throwable {
        if (page.isCounted()) {
            int total = this.processDefaultCount(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            page.setTotal(total);
            if (total > page.getStartNumber()) {
                Object result = invocation.proceed();
                page.setContent((List)result);
            }

            return page.getContent();
        } else {
            return invocation.proceed();
        }
    }

    private int processDefaultCount(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String msId = ms.getId();
        String countMsId = msId.substring(0, msId.length() - 4) + "count";
        int count = false;
        MappedStatement countMs = this.getExistedMappedStatement(ms.getConfiguration(), countMsId);
        if (countMs != null) {
            int count = this.doDefaultCount(executor, countMs, parameter, boundSql, resultHandler);
            return count;
        } else {
            throw new BusinessException("Mybatis Mapper接口缺少count方法！");
        }
    }

    private MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;

        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (Throwable var5) {
        }

        return mappedStatement;
    }

    private int doDefaultCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql, ResultHandler resultHandler) throws SQLException {
        CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        Map<String, Object> parameterMap = (Map)parameter;
        Object countParameter = parameterMap.get("criterion");
        BoundSql countBoundSql = countMs.getBoundSql(countParameter);
        Object countResultList = executor.query(countMs, countParameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
        return countResultList != null && !((List)countResultList).isEmpty() ? ((Number)((List)countResultList).get(0)).intValue() : 0;
    }

    private Object processCustomPage(PageDialect dialect, Page page, Invocation invocation, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws Throwable {
        if (page.isCounted()) {
            int total = this.processCustomCount(dialect, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
            page.setTotal(total);
            if (total > page.getStartNumber()) {
                Object result = this.doCustomPage(dialect, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
                page.setContent((List)result);
            }

            return page.getContent();
        } else {
            return this.doCustomPage(dialect, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
        }
    }

    private Object doCustomPage(PageDialect dialect, Invocation invocation, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws Throwable {
        List<ParameterMapping> parameterMappings = (List)dialect.processPageParameter(ms, parameter, boundSql, cacheKey);
        String pageSql = dialect.getPageSql(ms, boundSql, parameter, rowBounds, cacheKey);
        boundSql = PageQueryUtil.pageBoundSql(ms, boundSql, pageSql, parameterMappings, parameter);
        Object result = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
        return result;
    }

    private int processCustomCount(PageDialect dialect, Invocation invocation, Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql, CacheKey cacheKey) throws Throwable {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String countSql = CountSqlParser.getSmartCountSql(boundSql.getSql());
        MappedStatement countMs = PageQueryUtil.countMappedStatement(ms);
        BoundSql countBoundSql = PageQueryUtil.countBoundSql(countMs, boundSql, countSql, parameterMappings, parameter);
        int count = false;
        if (countMs != null) {
            int count = this.doCustomCount(executor, countMs, parameter, countBoundSql, cacheKey, resultHandler);
            return count;
        } else {
            throw new BusinessException("Mybatis Mapper接口缺少count方法！");
        }
    }

    private int doCustomCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql countBoundSql, CacheKey cacheKey, ResultHandler resultHandler) throws SQLException {
        Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, countBoundSql);
        return countResultList != null && !((List)countResultList).isEmpty() ? ((Number)((List)countResultList).get(0)).intValue() : 0;
    }
}
