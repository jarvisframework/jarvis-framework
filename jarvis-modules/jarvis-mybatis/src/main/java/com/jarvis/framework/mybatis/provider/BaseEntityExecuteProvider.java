package com.jarvis.framework.mybatis.provider;

import com.jarvis.framework.core.entity.BaseIdPrimaryKeyEntity;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.criteria.AbstractCriteriaCondition;
import com.jarvis.framework.mybatis.constant.ScriptBindConstant;
import com.jarvis.framework.mybatis.mapping.CrudDialectFactory;
import com.jarvis.framework.mybatis.update.CriteriaDelete;
import com.jarvis.framework.mybatis.update.CriteriaUpdate;
import com.jarvis.framework.mybatis.util.MapperEntityUtil;
import com.jarvis.framework.mybatis.util.PersistentUtil;
import com.jarvis.framework.search.CriteriaQuery;
import com.jarvis.framework.search.MultipleQuery;
import com.jarvis.framework.search.Page;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月14日
 */
public class BaseEntityExecuteProvider implements ProviderMethodResolver {

    /**
     * 新增
     *
     * @param entity
     * @return SQL
     */
    public static String insert(BaseIdPrimaryKeyEntity<?> entity) {
        return MapperEntityUtil.mapperEntity2InsertSql(entity);
    }

    /**
     * 修改
     *
     * @param entity
     * @return SQL
     */
    public static String update(BaseIdPrimaryKeyEntity<?> entity) {
        return MapperEntityUtil.mapperEntity2UpdateSql(entity);
    }

    /**
     * 删除
     *
     * @param entity
     * @return SQL
     */
    public static String delete(BaseIdPrimaryKeyEntity<?> entity) {
        return MapperEntityUtil.mapperEntity2DeleteSql(entity);
    }

    /**
     * 批量新增
     *
     * @param entity
     * @return SQL
     */
    public static String insertAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        return CrudDialectFactory.getDialect().insertAll(entities);
    }

    /**
     * 批量修改
     *
     * @param entity
     * @return SQL
     */
    public static String updateAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        return CrudDialectFactory.getDialect().updateAll(entities);
    }

    /**
     * 批量删除
     *
     * @param entity
     * @return SQL
     */
    public static String deleteAll(Collection<BaseIdPrimaryKeyEntity<?>> entities) {
        return CrudDialectFactory.getDialect().deleteAll(entities);
    }

    /**
     * 根据条件更新
     *
     * @param context
     * @return SQL
     */
    public static String updateBy(ProviderContext context, CriteriaUpdate<?> criterion) {

        processTableName(context, criterion);
        final StringBuilder sql = new StringBuilder();
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.UPDATE_BIND_VALUE).append("\"/>\n")
                .append("UPDATE ").append(criterion.getTableName()).append(" SET  ${_param.columns} ").append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     * 根据条件删除
     *
     * @param context
     * @return SQL
     */
    public static String deleteBy(ProviderContext context, CriteriaDelete<?> criterion) {

        processTableName(context, criterion);
        final StringBuilder sql = new StringBuilder();
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.DELETE_BIND_VALUE).append("\"/>\n")
                .append("DELETE FROM ").append(criterion.getTableName()).append(" \n")
                .append("<where> ${_param.where} </where>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     * 根据条件查询单个对象
     *
     * @param context
     * @return SQL
     */
    public static String getBy(ProviderContext context, CriteriaQuery<?> criterion) {

        processTableName(context, criterion);

        return CrudDialectFactory.getDialect().getBy(criterion);
    }

    /**
     * 根据条件查询单个对象(多表级联查询)
     *
     * @param context
     * @return SQL
     */
    public static String getObjectBy(ProviderContext context, Map<String, Object> parameter) {

        final CriteriaQuery<?> criterion = (CriteriaQuery<?>) parameter.get("criterion");

        processTableName(context, criterion);

        return getBy(context, criterion);
    }

    /**
     * 根据条件查询单个对象(多表级联查询)
     *
     * @param context
     * @return SQL
     */
    public static String getByMultiple(ProviderContext context, Map<String, Object> parameter) {

        final MultipleQuery criterion = (MultipleQuery) parameter.get("criterion");

        processTableName(context, criterion);

        return CrudDialectFactory.getDialect().getBy(criterion);
    }

    /**
     *
     * @param context
     * @return SQL
     */
    public static String queryBy(ProviderContext context, CriteriaQuery<?> criterion) {

        processTableName(context, criterion);

        final StringBuilder sql = new StringBuilder();
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT ${_param.columns} FROM ").append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy.length > 0'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");

        return sql.toString();
    }

    public static String queryObjectsBy(ProviderContext context, Map<String, Object> parameter) {

        final CriteriaQuery<?> criterion = (CriteriaQuery<?>) parameter.get("criterion");

        return queryBy(context, criterion);

    }

    /**
     *
     * @param context
     * @return SQL
     */
    public static String queryByMultiple(ProviderContext context, Map<String, Object> parameter) {

        final MultipleQuery criterion = (MultipleQuery) parameter.get("criterion");
        processTableName(context, criterion);

        final StringBuilder sql = new StringBuilder();
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT ${_param.columns} FROM ").append(criterion.getTableName()).append(" ")
                .append(criterion.getAlias()).append("\n")
                .append("${_param.join}")
                .append("<where> ${_param.where} </where>\n")
                .append("<if test='_param.groupBy != null and _param.groupBy != \"\"'>")
                .append("GROUP BY ${_param.groupBy}")
                .append("</if>\n")
                .append("<if test='_param.orderBy != null and _param.orderBy.length > 0'>")
                .append("ORDER BY ${_param.orderBy}")
                .append("</if>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     * 根据条件统计
     *
     * @param context 上下文
     * @param criterion 条件
     * @return SQL
     */
    public static String count(ProviderContext context, CriteriaQuery<?> criterion) {

        processTableName(context, criterion);

        final StringBuilder sql = new StringBuilder();
        //添加参数转换绑定
        sql.append("<script>\n")
                .append("<bind name=\"_param\" value=\"").append(ScriptBindConstant.QUERY_BIND_VALUE).append("\"/>\n")
                .append("SELECT count(1) FROM ").append(criterion.getTableName()).append("\n")
                .append("<where> ${_param.where} </where>\n")
                .append("</script>");

        return sql.toString();
    }

    /**
     * 分页SQL
     *
     * @param context 上下文
     * @param parameter 条件参数集
     * @return SQL
     */
    public static String page(ProviderContext context, Map<String, Object> parameter) {

        final Page page = (Page) parameter.get("page");
        final CriteriaQuery<?> criterion = (CriteriaQuery<?>) parameter.get("criterion");

        if (!StringUtils.hasText(criterion.getTableName())) {
            final String tableName = PersistentUtil.getTableName(context);
            if (!StringUtils.hasText(tableName)) {
                throw new FrameworkException("查询表名未设置");
            }
            criterion.tableName(tableName);
        }

        return CrudDialectFactory.getDialect().page(page, criterion);
    }

    /**
     * 根据条件查询是否存在
     *
     * @param context 上下文
     * @param criterion 条件
     * @return SQL
     */
    public static String exists(ProviderContext context, CriteriaQuery<?> criterion) {

        processTableName(context, criterion);

        return CrudDialectFactory.getDialect().exists(criterion);
    }

    private static void processTableName(ProviderContext context, AbstractCriteriaCondition<?, ?> criterion) {
        criterion.applyColumnLabel();
        if (StringUtils.hasText(criterion.getTableName())) {
            return;
        }
        final Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = PersistentUtil.getMapperEntityClazz(context);
        final String tableName = PersistentUtil.getTableName(clazz);
        if (StringUtils.hasText(tableName)) {
            criterion.tableName(tableName);
        }
    }

    private static void processTableName(ProviderContext context, CriteriaQuery<?> criterion) {
        criterion.applyColumnLabel();
        criterion.validate();
        /*if (BaseSimpleEntity.class.isAssignableFrom(clazz)) {
            if (null == criterion.getColumns() || criterion.getColumns().isEmpty()) {
                setSimpleEntityQueryColumns(criterion, (Class<? extends BaseSimpleEntity<?>>) clazz);
            }
        }*/
        if (StringUtils.hasText(criterion.getTableName())) {
            return;
        }
        final Class<? extends BaseIdPrimaryKeyEntity<?>> clazz = PersistentUtil.getMapperEntityClazz(context);
        final String tableName = PersistentUtil.getTableName(clazz);
        if (StringUtils.hasText(tableName)) {
            criterion.tableName(tableName);
        }
        if (!StringUtils.hasText(criterion.getTableName())) {
            throw new FrameworkException("查询表名未设置");
        }
    }

    /*private static void setSimpleEntityQueryColumns(CriteriaQuery<?> criterion,
        Class<? extends BaseSimpleEntity<?>> clazz) {
        try {
            final Set<String> columns = criterion.getColumns();
            columns.addAll(MapperEntityUtil.getPersistentColumns(clazz));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }*/
}
