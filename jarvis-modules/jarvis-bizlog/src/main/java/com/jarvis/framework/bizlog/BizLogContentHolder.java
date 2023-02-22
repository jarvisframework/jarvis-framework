package com.jarvis.framework.bizlog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年8月19日
 */
public final class BizLogContentHolder {

    private static InheritableThreadLocal<List<String>> contentHolder = new InheritableThreadLocal<>();

    public static void clearContent() {
        contentHolder.remove();
    }

    public static List<String> getContent() {
        return contentHolder.get();
    }

    public static void putContent(String content) {
        List<String> list = contentHolder.get();
        if (null == list) {
            list = new ArrayList<>(8);
        }
        list.add(content);
        contentHolder.set(list);
    }

}
