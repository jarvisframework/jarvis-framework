package com.jarvis.framework.search;

import java.io.Serializable;

/**
 * 排序
 *
 * @author qiucs
 * @version 1.0.0 2021年1月13日
 */
public class OrderBy implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4662169093368518740L;

    private String column;

    private OrderByEnum orderBy;

    public OrderBy() {
    }

    /**
     * @param column
     * @param orderBy
     */
    public OrderBy(String column, OrderByEnum orderBy) {
        super();
        this.column = column;
        this.orderBy = orderBy;
    }

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the orderBy
     */
    public OrderByEnum getOrderBy() {
        return orderBy;
    }

    /**
     * @param orderBy
     *            the orderBy to set
     */
    public void setOrderBy(OrderByEnum orderBy) {
        this.orderBy = orderBy;
    }

}
