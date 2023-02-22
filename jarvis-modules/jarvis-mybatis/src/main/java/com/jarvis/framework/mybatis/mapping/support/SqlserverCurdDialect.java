package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.mapping.AbstractCurdDialect;
import com.jarvis.framework.mybatis.mapping.CurdDialect;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月20日
 */
public class SqlserverCurdDialect extends AbstractCurdDialect {

    public static final CurdDialect INSTANCE = new SqlserverCurdDialect();

    @Override
    public String getBy(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT top(1) \n")
                .append("${_param.columns}\n")
                .append("FROM \n")
                .append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy !=\"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");

        return sql.toString();
    }

    @Override
    public String getBy(MultipleQuery criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT top(1) \n")
                .append("${_param.columns}\n")
                .append("FROM \n")
                .append(criterion.getTableName()).append(" ").append(criterion.getAlias()).append("\n")
                .append("${_param.join}")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy !=\"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     * 只支持sqlserver2012及以上版本分页
     *
     * @see com.jarvis.framework.mybatis.mapping.AbstractCurdDialect#page(com.jarvis.framework.search.Page,
     *      com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String page(Page page, CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        final int pageNumber = page.getPageNumber();
        final int pageSize = page.getPageSize();
        //order by xx offset 4 rows fetch next 5 rows only
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("<bind name=\"limit\" value=\"").append(pageSize).append("\"/>\n")
                .append("<bind name=\"offset\" value=\"").append((pageNumber - 1) * pageSize).append("\"/>\n")
                .append("SELECT ${_param.columns} FROM \n")
                .append("(SELECT id AS id_ FROM ").append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<choose>\n")
                .append("<when  test='_param.orderBy != null and _param.orderBy !=\"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</when >\n")
                .append("<otherwise >")
                .append("ORDER BY id")
                .append("</otherwise >\n")
                .append("</choose>\n")
                .append(" OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY) tmp_, ").append(criterion.getTableName())
                .append(" t WHERE tmp_.id_=t.id")
                .append("<if  test='_param.orderBy != null and _param.orderBy !=\"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");
        return sql.toString();
    }

    @Override
    public String exists(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT top(1) count(1) FROM \n")
                .append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("</script>");

        return sql.toString();
    }
}
