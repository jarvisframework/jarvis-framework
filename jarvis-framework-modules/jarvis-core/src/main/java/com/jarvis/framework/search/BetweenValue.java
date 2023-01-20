package com.jarvis.framework.search;

import java.io.Serializable;

public class BetweenValue implements Serializable {

    private static final long serialVersionUID = -1702347805066646905L;

    private Object startValue;

    private Object endValue;

    public BetweenValue(Object startValue, Object endValue) {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public Object getStartValue() {
        return this.startValue;
    }

    public void setStartValue(Object startValue) {
        this.startValue = startValue;
    }

    public Object getEndValue() {
        return this.endValue;
    }

    public void setEndValue(Object endValue) {
        this.endValue = endValue;
    }
}
