package com.jarvis.framework.mybatis.plugin.page;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年2月7日
 */
public abstract class CountSqlParser {

    public static final String KEEP_ORDERBY = "/*keep orderby*/";

    private static final Alias TABLE_ALIAS;

    //<editor-fold desc="聚合函数">
    private static final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet<String>());

    private static final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet<String>());

    /**
     * 聚合函数，以下列函数开头的都认为是聚合函数
     */
    private static final Set<String> AGGREGATE_FUNCTIONS = new HashSet<String>(Arrays.asList(
            ("APPROX_COUNT_DISTINCT," +
                    "ARRAY_AGG," +
                    "AVG," +
                    "BIT_," +
                    //"BIT_AND," +
                    //"BIT_OR," +
                    //"BIT_XOR," +
                    "BOOL_," +
                    //"BOOL_AND," +
                    //"BOOL_OR," +
                    "CHECKSUM_AGG," +
                    "COLLECT," +
                    "CORR," +
                    //"CORR_," +
                    //"CORRELATION," +
                    "COUNT," +
                    //"COUNT_BIG," +
                    "COVAR," +
                    //"COVAR_POP," +
                    //"COVAR_SAMP," +
                    //"COVARIANCE," +
                    //"COVARIANCE_SAMP," +
                    "CUME_DIST," +
                    "DENSE_RANK," +
                    "EVERY," +
                    "FIRST," +
                    "GROUP," +
                    //"GROUP_CONCAT," +
                    //"GROUP_ID," +
                    //"GROUPING," +
                    //"GROUPING," +
                    //"GROUPING_ID," +
                    "JSON_," +
                    //"JSON_AGG," +
                    //"JSON_ARRAYAGG," +
                    //"JSON_OBJECT_AGG," +
                    //"JSON_OBJECTAGG," +
                    //"JSONB_AGG," +
                    //"JSONB_OBJECT_AGG," +
                    "LAST," +
                    "LISTAGG," +
                    "MAX," +
                    "MEDIAN," +
                    "MIN," +
                    "PERCENT_," +
                    //"PERCENT_RANK," +
                    //"PERCENTILE_CONT," +
                    //"PERCENTILE_DISC," +
                    "RANK," +
                    "REGR_," +
                    "SELECTIVITY," +
                    "STATS_," +
                    //"STATS_BINOMIAL_TEST," +
                    //"STATS_CROSSTAB," +
                    //"STATS_F_TEST," +
                    //"STATS_KS_TEST," +
                    //"STATS_MODE," +
                    //"STATS_MW_TEST," +
                    //"STATS_ONE_WAY_ANOVA," +
                    //"STATS_T_TEST_*," +
                    //"STATS_WSR_TEST," +
                    "STD," +
                    //"STDDEV," +
                    //"STDDEV_POP," +
                    //"STDDEV_SAMP," +
                    //"STDDEV_SAMP," +
                    //"STDEV," +
                    //"STDEVP," +
                    "STRING_AGG," +
                    "SUM," +
                    "SYS_OP_ZONE_ID," +
                    "SYS_XMLAGG," +
                    "VAR," +
                    //"VAR_POP," +
                    //"VAR_SAMP," +
                    //"VARIANCE," +
                    //"VARIANCE_SAMP," +
                    //"VARP," +
                    "XMLAGG").split(",")));
    //</editor-fold>

    static {
        TABLE_ALIAS = new Alias("table_count");
        TABLE_ALIAS.setUseAs(false);
    }

    /**
     * 添加到聚合函数，可以是逗号隔开的多个函数前缀
     *
     * @param functions
     */
    public static void addAggregateFunctions(String functions) {
        if (StringUtils.hasText(functions)) {
            final String[] funs = functions.split(",");
            for (int i = 0; i < funs.length; i++) {
                AGGREGATE_FUNCTIONS.add(funs[i].toUpperCase());
            }
        }
    }

    /**
     * 获取智能的countSql
     *
     * @param sql
     * @return
     */
    public static String getSmartCountSql(String sql) {
        return getSmartCountSql(sql, "*");
    }

    /**
     * 获取智能的countSql
     *
     * @param sql
     * @param countColumn 列名，默认 0
     * @return
     */
    public static String getSmartCountSql(String sql, String countColumn) {
        //解析SQL
        Statement stmt = null;
        //特殊sql不需要去掉order by时，使用注释前缀
        if (sql.indexOf(KEEP_ORDERBY) >= 0) {
            return getSimpleCountSql(sql, countColumn);
        }
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (final Throwable e) {
            //无法解析的用一般方法返回count语句
            return getSimpleCountSql(sql, countColumn);
        }
        final Select select = (Select) stmt;
        final SelectBody selectBody = select.getSelectBody();
        try {
            //处理body-去order by
            processSelectBody(selectBody);
        } catch (final Exception e) {
            //当 sql 包含 group by 时，不去除 order by
            return getSimpleCountSql(sql, countColumn);
        }
        //处理with-去order by
        processWithItemsList(select.getWithItemsList());
        //处理为count查询
        sqlToCount(select, countColumn);
        final String result = select.toString();
        return result;
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    public static String getSimpleCountSql(final String sql) {
        return getSimpleCountSql(sql, "*");
    }

    /**
     * 获取普通的Count-sql
     *
     * @param sql 原查询sql
     * @return 返回count查询sql
     */
    public static String getSimpleCountSql(final String sql, String name) {
        final StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("select count(");
        stringBuilder.append(name);
        stringBuilder.append(") from ( \n");
        stringBuilder.append(sql);
        stringBuilder.append("\n ) tmp_count");
        return stringBuilder.toString();
    }

    /**
     * 将sql转换为count查询
     *
     * @param select
     */
    private static void sqlToCount(Select select, String name) {
        final SelectBody selectBody = select.getSelectBody();
        // 是否能简化count查询
        final List<SelectItem> COUNT_ITEM = new ArrayList<SelectItem>();
        COUNT_ITEM.add(new SelectExpressionItem(new Column("count(" + name + ")")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect) selectBody)) {
            ((PlainSelect) selectBody).setSelectItems(COUNT_ITEM);
        } else {
            final PlainSelect plainSelect = new PlainSelect();
            final SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }
    }

    /**
     * 是否可以用简单的count查询方式
     *
     * @param select
     * @return
     */
    private static boolean isSimpleCount(PlainSelect select) {
        //包含group by的时候不可以
        if (select.getGroupBy() != null) {
            return false;
        }
        //包含distinct的时候不可以
        if (select.getDistinct() != null) {
            return false;
        }
        //#606,包含having时不可以
        if (select.getHaving() != null) {
            return false;
        }
        for (final SelectItem item : select.getSelectItems()) {
            //select列中包含参数的时候不可以，否则会引起参数个数错误
            if (item.toString().contains("?")) {
                return false;
            }
            //如果查询列中包含函数，也不可以，函数可能会聚合列
            if (item instanceof SelectExpressionItem) {
                final Expression expression = ((SelectExpressionItem) item).getExpression();
                if (expression instanceof Function) {
                    final String name = ((Function) expression).getName();
                    if (name != null) {
                        final String NAME = name.toUpperCase();
                        if (skipFunctions.contains(NAME)) {
                            //go on
                        } else if (falseFunctions.contains(NAME)) {
                            return false;
                        } else {
                            for (final String aggregateFunction : AGGREGATE_FUNCTIONS) {
                                if (NAME.startsWith(aggregateFunction)) {
                                    falseFunctions.add(NAME);
                                    return false;
                                }
                            }
                            skipFunctions.add(NAME);
                        }
                    }
                } else if (expression instanceof Parenthesis && ((SelectExpressionItem) item).getAlias() != null) {
                    //#555，当存在 (a+b) as c 时，c 如果出现了 order by 或者 having 中时，会找不到对应的列，
                    // 这里想要更智能，需要在整个SQL中查找别名出现的位置，暂时不考虑，直接排除
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 处理selectBody去除Order by
     *
     * @param selectBody
     */
    private static void processSelectBody(SelectBody selectBody) {
        if (selectBody != null) {
            if (selectBody instanceof PlainSelect) {
                processPlainSelect((PlainSelect) selectBody);
            } else if (selectBody instanceof WithItem) {
                final WithItem withItem = (WithItem) selectBody;
                if (withItem.getSubSelect() != null) {
                    processSelectBody(withItem.getSubSelect().getSelectBody());
                }
            } else {
                final SetOperationList operationList = (SetOperationList) selectBody;
                if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                    final List<SelectBody> plainSelects = operationList.getSelects();
                    for (final SelectBody plainSelect : plainSelects) {
                        processSelectBody(plainSelect);
                    }
                }
                if (!orderByHashParameters(operationList.getOrderByElements())) {
                    operationList.setOrderByElements(null);
                }
            }
        }
    }

    /**
     * 处理PlainSelect类型的selectBody
     *
     * @param plainSelect
     */
    private static void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            final List<Join> joins = plainSelect.getJoins();
            for (final Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    /**
     * 处理WithItem
     *
     * @param withItemsList
     */
    private static void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            for (final WithItem item : withItemsList) {
                if (item.getSubSelect() != null) {
                    processSelectBody(item.getSubSelect().getSelectBody());
                }
            }
        }
    }

    /**
     * 处理子查询
     *
     * @param fromItem
     */
    private static void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            final SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                for (final Join join : subJoin.getJoinList()) {
                    if (join.getRightItem() != null) {
                        processFromItem(join.getRightItem());
                    }
                }
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            final SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {

        } else if (fromItem instanceof LateralSubSelect) {
            final LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                final SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
        //Table时不用处理
    }

    /**
     * 判断Orderby是否包含参数，有参数的不能去
     *
     * @param orderByElements
     * @return
     */
    private static boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (final OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().contains("?")) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        final String sql = "select * from city a, country b where a.c_id=b.id and name = ? order by time";

        System.out.println(getSmartCountSql(sql));
    }
}
