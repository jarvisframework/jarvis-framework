package com.jarvis.framework.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月13日
 */
public class Page implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4308574544048151442L;

    private int pageSize = 10;

    private int pageNumber = 1;

    private List<?> content = new ArrayList<>();

    private int total;

    private boolean counted = true;

    private int costTime;

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the content
     */
    public List<?> getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<?> content) {
        this.content = content;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the counted
     */
    public boolean isCounted() {
        return counted;
    }

    /**
     * @param counted the counted to set
     */
    public void setCounted(boolean counted) {
        this.counted = counted;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getStartNumber() {
        return (pageNumber - 1) * pageSize;
    }

    public int getEndNumber() {
        return pageNumber * pageSize;
    }

    /**
     * @return the costTime
     */
    public int getCostTime() {
        return costTime;
    }

    /**
     * @param costTime the costTime to set
     */
    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

}
