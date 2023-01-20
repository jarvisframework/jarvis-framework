package com.jarvis.framework.bizlog;

import java.util.ArrayList;
import java.util.List;

public final class BizLogContentHolder {
    private static InheritableThreadLocal<List<String>> contentHolder = new InheritableThreadLocal();

    public BizLogContentHolder() {
    }

    public static void clearContent() {
        contentHolder.remove();
    }

    public static List<String> getContent() {
        return (List)contentHolder.get();
    }

    public static void putContent(String content) {
        List<String> list = (List)contentHolder.get();
        if (list == null) {
            list = new ArrayList(8);
        }

        ((List)list).add(content);
        contentHolder.set(list);
    }
}
