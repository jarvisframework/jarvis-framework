package com.jarvis.framework.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page implements Serializable {

    private static final long serialVersionUID = 4308574544048151442L;

    private int pageSize = 10;

    private int pageNumber = 1;

    private List<?> content = new ArrayList();

    private int total;

    private boolean counted = true;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getContent() {
        return this.content;
    }

    public void setContent(List<?> content) {
        this.content = content;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isCounted() {
        return this.counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getStartNumber() {
        return (this.pageNumber - 1) * this.pageSize;
    }

    public int getEndNumber() {
        return this.pageNumber * this.pageSize;
    }
}
