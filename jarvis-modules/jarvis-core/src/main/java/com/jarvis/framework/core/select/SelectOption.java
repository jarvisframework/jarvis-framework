package com.jarvis.framework.core.select;

import java.util.HashMap;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年5月5日
 */
public class SelectOption extends HashMap<String, Object> {

    private static final String KEY_VALUE = "value";

    private static final String KEY_TEXT = "text";

    private static final String KEY_HIDDEN = "hidden";

    /**
     *
     */
    private static final long serialVersionUID = -2501695086850860737L;

    /**
     * 创建一个对象
     *
     * @return SelectOption
     */
    public static SelectOption create() {
        return new SelectOption();
    }

    public SelectOption value(Object value) {
        put(KEY_VALUE, value);
        return this;
    }

    public SelectOption text(String text) {
        put(KEY_TEXT, text);
        return this;
    }

    public SelectOption hidden(boolean hidden) {
        put(KEY_HIDDEN, hidden);
        return this;
    }

    public SelectOption hidden(Integer hidden) {
        put(KEY_HIDDEN, 1 == hidden);
        return this;
    }

    public SelectOption hidden(String hidden) {
        put(KEY_HIDDEN, "1".equals(hidden) || "yes".equalsIgnoreCase(hidden) || "true".equalsIgnoreCase(hidden));
        return this;
    }

    public Object value() {
        return get(KEY_VALUE);
    }

    public String text() {
        return String.valueOf(get(KEY_TEXT));
    }

    public boolean hidden() {
        if (containsKey(KEY_HIDDEN)) {
            final Object object = get(KEY_HIDDEN);
            if (null != object) {
                if (object.getClass().isAssignableFrom(boolean.class)) {
                    return (boolean) object;
                }
                if (object instanceof Boolean) {
                    return ((Boolean) object).booleanValue();
                }
            }
        }

        return false;
    }

}
