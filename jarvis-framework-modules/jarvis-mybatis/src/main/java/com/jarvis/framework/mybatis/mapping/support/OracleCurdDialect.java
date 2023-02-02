package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHolder;
import com.jarvis.framework.mybatis.mapping.AbstractCurdDialect;
import com.jarvis.framework.mybatis.mapping.CurdDialect;
import com.jarvis.framework.mybatis.parser.EntityProvideParser;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;

import java.util.Collection;
import java.util.Iterator;

public class OracleCurdDialect extends AbstractCurdDialect {
    public static final CurdDialect INSTANCE = new OracleCurdDialect();

    public String insertAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        BaseIdPrimaryKeyEntity<?> entity = (BaseIdPrimaryKeyEntity)iterator.next();
        String tableName = PersistentUtil.getTableName(entity);
        Collection<String> fields = PersistentUtil.getPersistentFields(entity);
        entities.forEach((e) -> {
            PersistentUtil.setEntityIdPrimaryKey(e);
            EntityAutoFillingHolder.insert(e);
        });
        StringBuilder cols = new StringBuilder(64);
        StringBuilder vals = new StringBuilder(64);
        fields.forEach((field) -> {
            cols.append(PersistentUtil.fieldToColumn(field)).append(",");
            vals.append(PersistentUtil.getBindingField("item." + field)).append(",");
        });
        cols.setLength(cols.length() - 1);
        vals.setLength(vals.length() - 1);
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("INSERT ALL \n").append("<foreach collection=\"list\" item=\"item\" separator=\" \"> \n").append("  INTO ").append(tableName).append(" ").append("(").append(cols).append(")").append(" VALUES ").append("(").append(vals).append(")").append("\n").append("</foreach>\n").append("SELECT 1 FROM dual\n").append("</script>");
        return sql.toString();
    }

    public String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        BaseIdPrimaryKeyEntity<?> entity = (BaseIdPrimaryKeyEntity)iterator.next();
        String tableName = PersistentUtil.getTableName(entity);
        Collection<String> fields = PersistentUtil.getPersistentFields(entity);
        entities.forEach((e) -> {
            EntityAutoFillingHolder.update(e);
        });
        StringBuilder cols = new StringBuilder(64);
        fields.forEach((field) -> {
            if (!"id".equals(field)) {
                cols.append(PersistentUtil.fieldToColumn(field)).append("=").append(PersistentUtil.getBindingField("item." + field)).append(",");
            }
        });
        cols.setLength(cols.length() - 1);
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<foreach collection=\"list\" item=\"item\" open=\"begin\" close=\";\nend;\" separator=\";\">\n").append("  UPDATE ").append(tableName).append(" SET ").append(cols).append(" WHERE id=").append(PersistentUtil.getBindingField("item.id")).append("\n").append("</foreach>\n").append("</script>");
        return sql.toString();
    }

    public String deleteAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        BaseIdPrimaryKeyEntity<?> entity = (BaseIdPrimaryKeyEntity)iterator.next();
        String tableName = PersistentUtil.getTableName(entity);
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("DELETE FROM ").append(tableName).append(" WHERE id in \n").append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n").append(PersistentUtil.getBindingField("item.id")).append("\n").append("</foreach>\n").append("</script>");
        return sql.toString();
    }

    public String getBy(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n<bind name=\"_param\" value=\"@").append(EntityProvideParser.class.getName()).append("@parseQuery(_parameter)\"/>\n").append("SELECT * FROM (\n").append("SELECT \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append(") WHERE rownum=1").append("</script>");
        return sql.toString();
    }

    public String getBy(MultipleQuery criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append(" ").append(criterion.getAlias()).append("\n").append("${_param.join}\n").append("<where> ${_param.where} AND rownum=1 </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append("</script>");
        return sql.toString();
    }

    public String page(Page page, CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("<bind name=\"end\" value=\"").append(pageNumber * pageSize).append("\"/>\n").append("<bind name=\"start\" value=\"").append((pageNumber - 1) * pageSize).append("\"/>\n").append("SELECT ${_param.columns} FROM \n").append("(SELECT id AS id_, rn_ FROM \n").append("(SELECT id, rownum AS rn_ FROM \n").append("(SELECT id FROM ").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append(") WHERE rownum <![CDATA[<=]]> #{end}").append(") WHERE rn_ <![CDATA[>]]> #{start}").append(") tmp_, ").append(criterion.getTableName()).append(" t WHERE tmp_.id_=t.id ").append("ORDER BY tmp_.rn_").append("</script>");
        return sql.toString();
    }

    public String exists(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n<bind name=\"_param\" value=\"@").append(EntityProvideParser.class.getName()).append("@parseQuery(_parameter)\"/>\n").append("SELECT count(1) FROM ").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} AND rownum=1 </where>\n").append("</script>");
        return sql.toString();
    }
}
