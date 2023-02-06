package com.jarvis.framework.mybatis.mapping.support;

import com.jarvis.framework.constant.BaseFieldConstant;
import com.jarvis.framework.constant.SymbolConstant;
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

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月19日
 */
public class OracleCurdDialect extends AbstractCurdDialect {

    public static final CurdDialect INSTANCE = new OracleCurdDialect();

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
                .append("INSERT ALL \n")
                .append("<foreach collection=\"list\" item=\"item\" separator=\" \"> \n")
                .append("  INTO ").append(tableName).append(SymbolConstant.SPACE).append(SymbolConstant.OPEN_PARENTHESIS)
                .append(cols)
                .append(SymbolConstant.CLOSE_PARENTHESIS)
                .append(" VALUES ").append(SymbolConstant.OPEN_PARENTHESIS)
                .append(vals)
                .append(SymbolConstant.CLOSE_PARENTHESIS).append("\n")
                .append("</foreach>\n")
                .append("SELECT 1 FROM dual\n")
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
        entities.forEach(e -> {
            EntityAutoFillingHolder.update(e);
        });
        final StringBuilder cols = new StringBuilder(64);
        fields.forEach(field -> {
            if (BaseFieldConstant.ID.equals(field)) {
                return;
            }
            cols.append(PersistentUtil.fieldToColumn(field))
                    .append("=")
                    .append(PersistentUtil.getBindingField("item." + field))
                    .append(SymbolConstant.COMMA);
        });
        cols.setLength(cols.length() - 1);

        final StringBuilder sql = new StringBuilder(128);
        sql.append("<script>\n")
                .append(
                        "<foreach collection=\"list\" item=\"item\" open=\"begin\" close=\";\nend;\" separator=\";\">\n")
                .append("  UPDATE ").append(tableName).append(" SET ")
                .append(cols)
                .append(" WHERE id=").append(PersistentUtil.getBindingField("item.id"))
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
     * @see com.jarvis.framework.mybatis.mapping.AbstractCurdDialect#getBy(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String getBy(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n<bind name=\"_param\" value=\"@").append(EntityProvideParser.class.getName())
                .append("@parseQuery(_parameter)\"/>\n")
                .append("SELECT * FROM (\n")
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
                .append(") WHERE rownum=1")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.AbstractCurdDialect#getBy(com.jarvis.framework.search.MultipleQuery)
     */
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
                .append("<where> ${_param.where} AND rownum=1 </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.AbstractCurdDialect#page(com.jarvis.framework.search.Page,
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
                .append("<bind name=\"end\" value=\"").append(pageNumber * pageSize).append("\"/>\n")
                .append("<bind name=\"start\" value=\"").append((pageNumber - 1) * pageSize).append("\"/>\n")
                .append("SELECT ${_param.columns} FROM \n")
                .append("(SELECT id AS id_, rn_ FROM \n")
                .append("(SELECT id, rownum AS rn_ FROM \n")
                .append("(SELECT id FROM ").append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy != \"\"'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append(") WHERE rownum <![CDATA[<=]]> #{end} ")
                .append(") WHERE rn_ <![CDATA[>]]> #{start}")
                .append(") tmp_, ").append(criterion.getTableName()).append(" t WHERE tmp_.id_=t.id ")
                .append("ORDER BY tmp_.rn_")
                .append("</script>");
        return sql.toString();
    }

    /**
     *
     * @see com.jarvis.framework.mybatis.mapping.AbstractCurdDialect#exists(com.jarvis.framework.search.CriteriaQuery)
     */
    @Override
    public String exists(CriteriaQuery<?> criterion) {
        final StringBuilder sql = new StringBuilder(128);
        //添加参数转换绑定
        sql.append("<script>\n<bind name=\"_param\" value=\"@").append(EntityProvideParser.class.getName())
                .append("@parseQuery(_parameter)\"/>\n")
                .append("SELECT count(1) FROM ")
                .append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} AND rownum=1 </where>\n")
                .append("</script>");

        return sql.toString();
    }

    public static void main(String[] args) {
        /*final CurdSqlGenerator generator = new OracleCurdSqlGenerator();
        final List<LongIdEntity> list = new ArrayList<LongIdEntity>();
        DynamicLongIdEntity entity = new DynamicLongIdEntity();
        entity.tableName("city");
        entity.put("name", "a1");
        list.add(entity);
        entity = new DynamicLongIdEntity();
        entity.put("name", "a2");
        list.add(entity);
        entity = new DynamicLongIdEntity();
        entity.put("name", "a3");
        list.add(entity);
        System.out.println(generator.deleteAll(list));*/
    }

}
