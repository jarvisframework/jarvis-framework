package com.jarvis.framework.search;

import java.io.Serializable;

public class OrderBy implements Serializable {
    private static final long serialVersionUID = 4662169093368518740L;
    private String column;
    private OrderByEnum orderBy;

    public OrderBy() {
    }

    public OrderBy(String column, OrderByEnum orderBy) {
        this.column = column;
        this.orderBy = orderBy;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public OrderByEnum getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(OrderByEnum orderBy) {
        this.orderBy = orderBy;
    }
}
