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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年3月18日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class, CacheKey.class, BoundSql.class }),
})
public class PageInterceptor implements Interceptor {

    /**
     *
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[0];
        final Object parameter = args[1];
        final RowBounds rowBounds = (RowBounds) args[2];
        final PageDialect dialect = PageDialectFactory.getDialect();
        final ResultHandler resultHandler = (ResultHandler) args[3];
        final Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        if (ObjectQueryUtil.isObjectQuery(ms)) {
            return ObjectQueryUtil.doObjectQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql, cacheKey);
        }
        //
        if (dialect.skip(ms, parameter, rowBounds)) {
            return invocation.proceed();
        }

        final Page page = PageQueryUtil.getPage(parameter);

        if (isDefaultPage(ms)) {
            return processDefaultPage(page, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }

        return processCustomPage(dialect, page, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql,
                cacheKey);
    }

    private boolean isDefaultPage(MappedStatement ms) {
        final String id = ms.getId();
        if (id.endsWith(".page")) {
            return true;
        }
        return false;
    }

    private Object processDefaultPage(Page page, Invocation invocation, Executor executor, MappedStatement ms,
                                      Object parameter, RowBounds rowBounds,
                                      ResultHandler resultHandler, BoundSql boundSql) throws Throwable {
        if (page.isCounted()) {
            final int total = processDefaultCount(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            page.setTotal(total);
            if (total > page.getStartNumber()) {
                final Object result = invocation.proceed();
                page.setContent((List<?>) result);
            }
            return page.getContent();
        } else {
            return invocation.proceed();
        }
    }

    private int processDefaultCount(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                                    ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        final String msId = ms.getId();
        final String countMsId = msId.substring(0, msId.length() - 4) + "count";
        int count = 0;
        //先判断是否存在手写的 count 查询
        final MappedStatement countMs = getExistedMappedStatement(ms.getConfiguration(), countMsId);
        if (countMs != null) {
            count = doDefaultCount(executor, countMs, parameter, boundSql, resultHandler);
        } else {
            throw new BusinessException("Mybatis Mapper接口缺少count方法！");
        }
        return count;
    }

    /**
     * 尝试获取已经存在的在 MS，提供对手写count和page的支持
     *
     * @param configuration
     * @param msId
     * @return
     */
    private MappedStatement getExistedMappedStatement(Configuration configuration, String msId) {
        MappedStatement mappedStatement = null;
        try {
            mappedStatement = configuration.getMappedStatement(msId, false);
        } catch (final Throwable t) {
            //ignore
        }
        return mappedStatement;
    }

    private int doDefaultCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql boundSql,
                               ResultHandler resultHandler) throws SQLException {
        final CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
        final Map<String, Object> parameterMap = (Map<String, Object>) parameter;
        final Object countParameter = parameterMap.get("criterion");
        final BoundSql countBoundSql = countMs.getBoundSql(countParameter);
        final Object countResultList = executor.query(countMs, countParameter, RowBounds.DEFAULT,
                resultHandler, countKey,
                countBoundSql);
        //某些数据（如 TDEngine）查询 count 无结果时返回 null
        if (countResultList == null || ((List) countResultList).isEmpty()) {
            return 0;
        }
        return ((Number) ((List) countResultList).get(0)).intValue();
    }

    private Object processCustomPage(PageDialect dialect, Page page, Invocation invocation, Executor executor,
                                     MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql,
                                     CacheKey cacheKey) throws Throwable {
        if (page.isCounted()) {
            final int total = processCustomCount(dialect, invocation, executor, ms, parameter, rowBounds, resultHandler,
                    boundSql, cacheKey);
            page.setTotal(total);
            if (total > page.getStartNumber()) {
                final Object result = doCustomPage(dialect, invocation, executor, ms, parameter, rowBounds,
                        resultHandler, boundSql, cacheKey);
                page.setContent((List<?>) result);
            }
            return page.getContent();
        } else {
            return doCustomPage(dialect, invocation, executor, ms, parameter, rowBounds, resultHandler, boundSql,
                    cacheKey);
        }
    }

    private Object doCustomPage(PageDialect dialect, Invocation invocation, Executor executor,
                                MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql,
                                CacheKey cacheKey) throws Throwable {
        final List<ParameterMapping> parameterMappings = (List<ParameterMapping>) dialect.processPageParameter(ms,
                parameter, boundSql, cacheKey);
        final String pageSql = dialect.getPageSql(ms, boundSql, parameter, rowBounds, cacheKey);
        boundSql = PageQueryUtil.pageBoundSql(ms, boundSql, pageSql, parameterMappings, parameter);
        final Object result = executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, boundSql);
        return result;
    }

    private int processCustomCount(PageDialect dialect, Invocation invocation, Executor executor,
                                   MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql,
                                   CacheKey cacheKey) throws Throwable {
        final List<ParameterMapping> parameterMappings = (List<ParameterMapping>) boundSql.getParameterMappings();
        final String countSql = CountSqlParser.getSmartCountSql(boundSql.getSql());
        final MappedStatement countMs = PageQueryUtil.countMappedStatement(ms);
        final BoundSql countBoundSql = PageQueryUtil.countBoundSql(countMs, boundSql, countSql, parameterMappings,
                parameter);
        int count = 0;
        //先判断是否存在手写的 count 查询
        if (countMs != null) {
            count = doCustomCount(executor, countMs, parameter, countBoundSql, cacheKey, resultHandler);
        } else {
            throw new BusinessException("Mybatis Mapper接口缺少count方法！");
        }
        return count;
    }

    private int doCustomCount(Executor executor, MappedStatement countMs, Object parameter, BoundSql countBoundSql,
                              CacheKey cacheKey, ResultHandler resultHandler) throws SQLException {
        final Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, cacheKey,
                countBoundSql);
        //某些数据（如 TDEngine）查询 count 无结果时返回 null
        if (countResultList == null || ((List) countResultList).isEmpty()) {
            return 0;
        }
        return ((Number) ((List) countResultList).get(0)).intValue();
    }

}
