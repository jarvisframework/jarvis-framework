package com.jarvis.framework.mybatis.mapping;

import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.handler.EntityAutoFillingHolder;

import java.util.Collection;

public abstract class AbstractCurdDialect implements CurdDialect {

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
        sql.append("<script>\n").append("INSERT INTO ").append(tableName).append(" ").append("(").append(cols).append(")").append("\n").append(" VALUES \n");
        sql.append("<foreach collection=\"list\" item=\"item\" separator=\"), (\" open=\"(\" close=\")\"> \n").append(vals).append("\n");
        sql.append("</foreach>").append("\n").append("</script>");
        return sql.toString();
    }

    public String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        Iterator<BaseIdPrimaryKeyEntity<?>> iterator = entities.iterator();
        BaseIdPrimaryKeyEntity<?> entity = (BaseIdPrimaryKeyEntity)iterator.next();
        String tableName = PersistentUtil.getTableName(entity);
        Collection<String> fields = PersistentUtil.getPersistentFields(entity);
        String id = "id";
        fields.remove("id");
        entities.forEach((e) -> {
            EntityAutoFillingHolder.update(e);
        });
        StringBuilder cols = new StringBuilder(256);
        fields.forEach((field) -> {
            cols.append("<foreach collection=\"list\" item=\"item\" separator=\"\\n\" open=\"").append(PersistentUtil.fieldToColumn(field)).append(" = CASE id \" close=\"END,\"> \n").append(" WHEN id=").append(PersistentUtil.getBindingField("id")).append(" THEN ").append(PersistentUtil.getBindingField(field)).append("\n").append("</foreach>\n");
        });
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("UPDATE ").append(tableName).append("\n").append("<trim prefix=\"set\" suffixOverrides=\",\"> \n").append(cols).append("</trim>\n").append(" WHERE id in \n").append("<foreach collection=\"list\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n").append(PersistentUtil.getBindingField("item.id")).append("\n").append("</foreach>\n").append("</script>");
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

    public String deleteById(String tableName) {
        return "DELETE FROM " + tableName + " WHERE id=" + PersistentUtil.getBindingField("id");
    }

    public String deleteByIds(String tableName) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("DELETE FROM ").append(tableName).append(" WHERE id in \n").append("<foreach collection=\"ids\" item=\"id\" open=\"(\" close=\")\" separator=\",\">\n").append(PersistentUtil.getBindingField("id")).append("\n").append("</foreach>\n").append("</script>");
        return sql.toString();
    }

    public String getById(String tableName) {
        return "SELECT * FROM " + tableName + " WHERE id=" + PersistentUtil.getBindingField("id");
    }

    public String getBy(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append(" LIMIT 1 ").append("</script>");
        return sql.toString();
    }

    public String getBy(MultipleQuery criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT \n").append("${_param.columns}\n").append("FROM \n").append(criterion.getTableName()).append(" ").append(criterion.getAlias()).append("\n").append("${_param.join}\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append(" LIMIT 1 ").append("</script>");
        return sql.toString();
    }

    public String page(Page page, CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("<bind name=\"limit\" value=\"").append(pageSize).append("\"/>\n").append("<bind name=\"offset\" value=\"").append((pageNumber - 1) * pageSize).append("\"/>\n").append("SELECT ${_param.columns} FROM \n").append("(SELECT id AS id_ FROM ").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>").append("GROUP BY ${_param.groupBy}").append("</if>\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>\n").append(" LIMIT #{limit} OFFSET #{offset}) tmp_, ").append(criterion.getTableName()).append(" t WHERE tmp_.id_=t.id\n").append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>").append("ORDER BY ${_param.orderBy}").append("</if>").append("</script>");
        return sql.toString();
    }

    public String exists(CriteriaQuery<?> criterion) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n").append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n").append("SELECT count(1) FROM \n").append(criterion.getTableName()).append("\n").append("<where> ${_param.where} </where>\n").append(" LIMIT 1 ").append("</script>");
        return sql.toString();
    }
}
