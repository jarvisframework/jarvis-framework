package com.jarvis.framework.core.select;

import java.util.HashMap;

public class SelectOption extends HashMap<String, Object> {
    private static final String KEY_VALUE = "value";
    private static final String KEY_TEXT = "text";
    private static final String KEY_HIDDEN = "hidden";
    private static final long serialVersionUID = -2501695086850860737L;

    public SelectOption() {
    }

    public static SelectOption create() {
        return new SelectOption();
    }

    public SelectOption value(Object value) {
        this.put("value", value);
        return this;
    }

    public SelectOption text(String text) {
        this.put("text", text);
        return this;
    }

    public SelectOption hidden(boolean hidden) {
        this.put("hidden", hidden);
        return this;
    }

    public SelectOption hidden(Integer hidden) {
        this.put("hidden", 1 == hidden);
        return this;
    }

    public SelectOption hidden(String hidden) {
        this.put("hidden", "1".equals(hidden) || "yes".equalsIgnoreCase(hidden) || "true".equalsIgnoreCase(hidden));
        return this;
    }

    public Object value() {
        return this.get("value");
    }

    public String text() {
        return String.valueOf(this.get("text"));
    }

    public boolean hidden() {
        if (this.containsKey("hidden")) {
            Object object = this.get("hidden");
            if (null != object) {
                if (object.getClass().isAssignableFrom(Boolean.TYPE)) {
                    return (Boolean)object;
                }

                if (object instanceof Boolean) {
                    return (Boolean)object;
                }
            }
        }

        return false;
    }
}
