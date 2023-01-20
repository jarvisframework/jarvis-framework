package com.jarvis.framework.util;

public class CamelCaseUtil {

    public static String toLowerCamelCase(String column) {
        int len = column.length();
        StringBuilder sb = new StringBuilder(len);
        int underLineIndex = -9;
        if (isUpperCase(column)) {
            column = column.toLowerCase();
        }

        for(int i = 0; i < len; ++i) {
            char ch = column.charAt(i);
            if ('_' == column.charAt(i)) {
                underLineIndex = i;
            } else if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else if (0 == i && Character.isUpperCase(ch)) {
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    private static boolean isUpperCase(String str) {
        for(int i = 0; i < str.length(); ++i) {
            if (Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String toUpperCamelCase(String column) {
        int len = column.length();
        StringBuilder sb = new StringBuilder(len);
        int underLineIndex = -9;
        if (isUpperCase(column)) {
            column = column.toLowerCase();
        }

        for(int i = 0; i < len; ++i) {
            char ch = column.charAt(i);
            if ('_' == column.charAt(i)) {
                underLineIndex = i;
            } else if (i == 0) {
                sb.append(Character.toUpperCase(ch));
            } else if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public static String upperToLowerCamelCase(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toLowerCase(str.charAt(0)));
        return sb.toString();
    }
}
