package com.jarvis.framework.mybatis.plugin.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

public abstract class CountSqlParser {

    public static final String KEEP_ORDERBY = "/*keep orderby*/";

    private static final Alias TABLE_ALIAS = new Alias("table_count");

    private static final Set<String> skipFunctions = Collections.synchronizedSet(new HashSet());

    private static final Set<String> falseFunctions = Collections.synchronizedSet(new HashSet());

    private static final Set<String> AGGREGATE_FUNCTIONS = new HashSet(Arrays.asList("APPROX_COUNT_DISTINCT,ARRAY_AGG,AVG,BIT_,BOOL_,CHECKSUM_AGG,COLLECT,CORR,COUNT,COVAR,CUME_DIST,DENSE_RANK,EVERY,FIRST,GROUP,JSON_,LAST,LISTAGG,MAX,MEDIAN,MIN,PERCENT_,RANK,REGR_,SELECTIVITY,STATS_,STD,STRING_AGG,SUM,SYS_OP_ZONE_ID,SYS_XMLAGG,VAR,XMLAGG".split(",")));


    public static void addAggregateFunctions(String functions) {
        if (StringUtils.hasText(functions)) {
            String[] funs = functions.split(",");

            for(int i = 0; i < funs.length; ++i) {
                AGGREGATE_FUNCTIONS.add(funs[i].toUpperCase());
            }
        }

    }

    public static String getSmartCountSql(String sql) {
        return getSmartCountSql(sql, "*");
    }

    public static String getSmartCountSql(String sql, String countColumn) {
        Statement stmt = null;
        if (sql.indexOf("/*keep orderby*/") >= 0) {
            return getSimpleCountSql(sql, countColumn);
        } else {
            try {
                stmt = CCJSqlParserUtil.parse(sql);
            } catch (Throwable var7) {
                return getSimpleCountSql(sql, countColumn);
            }

            Select select = (Select)stmt;
            SelectBody selectBody = select.getSelectBody();

            try {
                processSelectBody(selectBody);
            } catch (Exception var6) {
                return getSimpleCountSql(sql, countColumn);
            }

            processWithItemsList(select.getWithItemsList());
            sqlToCount(select, countColumn);
            String result = select.toString();
            return result;
        }
    }

    public static String getSimpleCountSql(String sql) {
        return getSimpleCountSql(sql, "*");
    }

    public static String getSimpleCountSql(String sql, String name) {
        StringBuilder stringBuilder = new StringBuilder(sql.length() + 40);
        stringBuilder.append("select count(");
        stringBuilder.append(name);
        stringBuilder.append(") from ( \n");
        stringBuilder.append(sql);
        stringBuilder.append("\n ) tmp_count");
        return stringBuilder.toString();
    }

    private static void sqlToCount(Select select, String name) {
        SelectBody selectBody = select.getSelectBody();
        List<SelectItem> COUNT_ITEM = new ArrayList();
        COUNT_ITEM.add(new SelectExpressionItem(new Column("count(" + name + ")")));
        if (selectBody instanceof PlainSelect && isSimpleCount((PlainSelect)selectBody)) {
            ((PlainSelect)selectBody).setSelectItems(COUNT_ITEM);
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody(selectBody);
            subSelect.setAlias(TABLE_ALIAS);
            plainSelect.setFromItem(subSelect);
            plainSelect.setSelectItems(COUNT_ITEM);
            select.setSelectBody(plainSelect);
        }

    }

    private static boolean isSimpleCount(PlainSelect select) {
        if (select.getGroupBy() != null) {
            return false;
        } else if (select.getDistinct() != null) {
            return false;
        } else if (select.getHaving() != null) {
            return false;
        } else {
            Iterator var1 = select.getSelectItems().iterator();

            while(true) {
                String NAME;
                do {
                    String name;
                    label59:
                    do {
                        SelectItem item;
                        Expression expression;
                        do {
                            do {
                                if (!var1.hasNext()) {
                                    return true;
                                }

                                item = (SelectItem)var1.next();
                                if (item.toString().contains("?")) {
                                    return false;
                                }
                            } while(!(item instanceof SelectExpressionItem));

                            expression = ((SelectExpressionItem)item).getExpression();
                            if (expression instanceof Function) {
                                name = ((Function)expression).getName();
                                continue label59;
                            }
                        } while(!(expression instanceof Parenthesis) || ((SelectExpressionItem)item).getAlias() == null);

                        return false;
                    } while(name == null);

                    NAME = name.toUpperCase();
                } while(skipFunctions.contains(NAME));

                if (falseFunctions.contains(NAME)) {
                    return false;
                }

                Iterator var6 = AGGREGATE_FUNCTIONS.iterator();

                while(var6.hasNext()) {
                    String aggregateFunction = (String)var6.next();
                    if (NAME.startsWith(aggregateFunction)) {
                        falseFunctions.add(NAME);
                        return false;
                    }
                }

                skipFunctions.add(NAME);
            }
        }
    }

    private static void processSelectBody(SelectBody selectBody) {
        if (selectBody != null) {
            if (selectBody instanceof PlainSelect) {
                processPlainSelect((PlainSelect)selectBody);
            } else if (selectBody instanceof WithItem) {
                WithItem withItem = (WithItem)selectBody;
                if (withItem.getSubSelect() != null) {
                    processSelectBody(withItem.getSubSelect().getSelectBody());
                }
            } else {
                SetOperationList operationList = (SetOperationList)selectBody;
                if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                    List<SelectBody> plainSelects = operationList.getSelects();
                    Iterator var3 = plainSelects.iterator();

                    while(var3.hasNext()) {
                        SelectBody plainSelect = (SelectBody)var3.next();
                        processSelectBody(plainSelect);
                    }
                }

                if (!orderByHashParameters(operationList.getOrderByElements())) {
                    operationList.setOrderByElements((List)null);
                }
            }
        }

    }

    private static void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements((List)null);
        }

        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }

        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            Iterator var2 = joins.iterator();

            while(var2.hasNext()) {
                Join join = (Join)var2.next();
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }

    }

    private static void processWithItemsList(List<WithItem> withItemsList) {
        if (withItemsList != null && withItemsList.size() > 0) {
            Iterator var1 = withItemsList.iterator();

            while(var1.hasNext()) {
                WithItem item = (WithItem)var1.next();
                if (item.getSubSelect() != null) {
                    processSelectBody(item.getSubSelect().getSelectBody());
                }
            }
        }

    }

    private static void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin)fromItem;
            if (subJoin.getJoinList() != null && subJoin.getJoinList().size() > 0) {
                Iterator var2 = subJoin.getJoinList().iterator();

                while(var2.hasNext()) {
                    Join join = (Join)var2.next();
                    if (join.getRightItem() != null) {
                        processFromItem(join.getRightItem());
                    }
                }
            }

            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect)fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (!(fromItem instanceof ValuesList) && fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect)fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }

    }

    private static boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        } else {
            Iterator var1 = orderByElements.iterator();

            OrderByElement orderByElement;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                orderByElement = (OrderByElement)var1.next();
            } while(!orderByElement.toString().contains("?"));

            return true;
        }
    }

    public static void main(String[] args) {
        String sql = "select * from city a, country b where a.c_id=b.id and name = ? order by time";
        System.out.println(getSmartCountSql("select * from city a, country b where a.c_id=b.id and name = ? order by time"));
    }

    static {
        TABLE_ALIAS.setUseAs(false);
    }
}
