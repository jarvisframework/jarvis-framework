package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.constant.BaseFieldConstant;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHolder;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月19日
 */
public abstract class AbstractCurdDialect implements CurdDialect {

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#insertAll(java.util.Collection)
     */
    @Override
    public String insertAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        final Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        final BaseIdPrimaryKeyEntity<?> entity = iterator.next();
        final String tableName = PersistentUtil.getTableName(entity);
        final Collection<String> fields = PersistentUtil.getPersistentFields(entity);

        entities.forEach((e) -> {
            PersistentUtil.setEntityIdPrimaryKey(e);
            EntityAutoFillingHolder.insert(e);
        });

        final StringBuilder cols = new StringBuilder(64);
        final StringBuilder vals = new StringBuilder(64);
        fields.forEach(field -> {
            cols.append(PersistentUtil.fieldToColumn(field)).append(SymbolConstant.COMMA);
            vals.append(PersistentUtil.getBindingField("item." + field))
                    .append(SymbolConstant.COMMA);
        });
        cols.setLength(cols.length() - 1);
        vals.setLength(vals.length() - 1);

        final StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n")
                .append("INSERT INTO ").append(tableName).append(SymbolConstant.SPACE)
                .append(SymbolConstant.OPEN_PARENTHESIS)
                .append(cols)
                .append(SymbolConstant.CLOSE_PARENTHESIS).append("\n")
                .append(" VALUES \n");
        sql.append("<foreach collection=\"list\" item=\"item\" separator=\"), (\" open=\"(\" close=\")\"> \n")
                .append(vals).append("\n");
        sql.append("</foreach>").append("\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#updateAll(java.util.Collection)
     */
    @Override
    public String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        final Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        final BaseIdPrimaryKeyEntity<?> entity = iterator.next();
        final String tableName = PersistentUtil.getTableName(entity);
        final Collection<String> fields = PersistentUtil.getPersistentFields(entity);
        final String id = BaseFieldConstant.ID;
        fields.remove(id); //去掉id属性
        entities.forEach(e -> {
            EntityAutoFillingHolder.update(e);
        });
        final StringBuilder cols = new StringBuilder(256);

        fields.forEach(field -> {
            cols.append("<foreach collection=\"list\" item=\"item\" separator=\"\\n\" open=\"")
                    .append(PersistentUtil.fieldToColumn(field)).append(" = CASE id \" close=\"END,\"> \n")
                    .append(" WHEN id=").append(PersistentUtil.getBindingField(id)).append(" THEN ")
                    .append(PersistentUtil.getBindingField(field)).append("\n")
                    .append("</foreach>\n");
        });

        final StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n")
                .append("UPDATE ").append(tableName).append("\n")
                .append("<trim prefix=\"set\" suffixOverrides=\",\"> \n")
                .append(cols)
                .append("</trim>\n")
                .append(" WHERE id in \n")
                .append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n")
                .append(PersistentUtil.getBindingField("item.id"))
                .append("\n")
                .append("</foreach>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#deleteAll(java.util.Collection)
     */
    @Override
    public String deleteAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        final Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        final BaseIdPrimaryKeyEntity<?> entity = iterator.next();
        final String tableName = PersistentUtil.getTableName(entity);

        final StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n")
                .append("DELETE FROM ").append(tableName).append(" WHERE id in \n")
                .append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n")
                .append(PersistentUtil.getBindingField("item.id"))
                .append("\n")
                .append("</foreach>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#deleteById(java.lang.String)
     */
    @Override
    public String deleteById(String tableName) {
        return "DELETE FROM " + tableName + " WHERE id=" + PersistentUtil.getBindingField("id");
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#deleteByIds(java.lang.String)
     */
    @Override
    public String deleteByIds(String tableName) {
        final StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n")
                .append("DELETE FROM ").append(tableName).append(" WHERE id in \n")
                .append("<foreach collection=\"ids\" item=\"id\" open=\"(\" close=\")\" separator=\",\">\n")
                .append(PersistentUtil.getBindingField("id")).append("\n")
                .append("</foreach>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#getById(java.lang.String)
     */
    @Override
    public String getById(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE id=" + PersistentUtil.getBindingField("id");
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#getBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String getBy(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT \n")
                .append("${_param.columns}\n")
                .append("FROM \n")
                .append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append(" LIMIT 1 ")
                .append("</script>");

        return sql.toString();
    }

    @Override
    public String getBy(MultipleQuery criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT \n")
                .append("${_param.columns}\n")
                .append("FROM \n")
                .append(criterion.getTableName()).append(" ").append(criterion.getAlias()).append("\n")
                .append("${_param.join}\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append(" LIMIT 1 ")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#page(com.jarvis.framework.search.Page,
     *      com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String page(Page page, CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        final int pageNumber = page.getPageNumber();
        final int pageSize = page.getPageSize();
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
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append(" LIMIT #{limit} OFFSET #{offset}) tmp_, ").append(criterion.getTableName())
                .append(" t WHERE tmp_.id_=t.id\n")
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>")
                .append("</script>");
        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.CurdDialect#exists(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String exists(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT count(1) FROM \n")
                .append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append(" LIMIT 1 ")
                .append("</script>");

        return sql.toString();
    }

}
