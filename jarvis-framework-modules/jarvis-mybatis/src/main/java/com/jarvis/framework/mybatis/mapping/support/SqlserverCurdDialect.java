package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.mapping.AbstractCurdDialect;
import com.jarvis.framework.mybatis.mapping.CurdDialect;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;

public class SqlserverCurdDialect extends AbstractCurdDialect {

    public static final CurdDialect INSTANCE = new SqlserverCurdDialect();


    public String getBy(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT top(1) \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy !=\"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append("</script>");
        return sql.toString();
    }

    public String getBy(MultipleQuery criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT top(1) \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append(" ").append(criterion.getAlias()).append("\n").append("${_param.join}").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy !=\"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append("</script>");
        return sql.toString();
    }

    public String page(Page page, CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("<bind name=\"limit\" value=\"").append(pageSize).append("\"/>\n").append("<bind name=\"offset\" value=\"").append((pageNumber - 1) * pageSize).append("\"/>\n").append("SELECT ${_param.columns} FROM \n").append("(SELECT id AS id_ FROM ").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<choose>\n").append("<when  test='_param.orderBy != null and _param.orderBy !=\"\"'>").append("ORDER BY ${_param.orderBy}").append("</when >\n").append("<otherwise >").append("ORDER BY id").append("</otherwise >\n").append("</choose>\n").append(" OFFSET #{offset} ROWS FETCH NEXT #{limit} ROWS ONLY) tmp_, ").append(criterion.getTableName()).append(" t WHERE tmp_.id_=t.id").append("<if  test='_param.orderBy != null and _param.orderBy !=\"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append("</script>");
        return sql.toString();
    }

    public String exists(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT top(1) count(1) FROM \n").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("</script>");
        return sql.toString();
    }
}
