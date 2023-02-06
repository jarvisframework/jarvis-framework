package com.jarvis.framework.search;

import java.io.Serializable;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月13日
 */
public class BetweenValue implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1702347805066646905L;

    private Object startValue;

    private Object endValue;

    public BetweenValue() {

    }

    /**
     * @param startValue
     * @param endValue
     */
    public BetweenValue(Object startValue, Object endValue) {
        super();
        this.startValue = startValue;
        this.endValue = endValue;
    }

    /**
     * @return the startValue
     */
    public Object getStartValue() {
        return startValue;
    }

    /**
     * @param startValue
     *            the startValue to set
     */
    public void setStartValue(Object startValue) {
        this.startValue = startValue;
    }

    /**
     * @return the endValue
     */
    public Object getEndValue() {
        return endValue;
    }

    /**
     * @param endValue
     *            the endValue to set
     */
    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }
}
