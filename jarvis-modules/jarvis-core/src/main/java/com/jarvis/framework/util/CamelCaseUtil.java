package com.jarvis.framework.util;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月27日
 */
public class CamelCaseUtil {

    /**
     * 把字段转成小写驼峰格式： fonds_code => fondsCode <br>
     * 如果全大写的，则会先转全小写；
     * 如果没有下划线[_]，则直接返回
     *
     * @param column 字段
     * @return String
     */
    public static String toLowerCamelCase(String column) {
        final int len = column.length();
        final StringBuilder sb = new StringBuilder(len);
        char ch;
        int underLineIndex = -9;
        if (isUpperCase(column)) {
            column = column.toLowerCase();
        }
        for (int i = 0; i < len; i++) {
            ch = column.charAt(i);
            if ('_' == column.charAt(i)) {
                underLineIndex = i;
                continue;
            }
            if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else if (0 == i && Character.isUpperCase(ch)) {
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否全大写
     *
     * @param str 字符
     * @return boolean
     */
    private static boolean isUpperCase(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把字段转成大写驼峰格式： fonds_code => FondsCode <br>
     * 如果全大写的，则会先转全小写；
     *
     * @param column 字段
     * @return String
     */
    public static String toUpperCamelCase(String column) {
        final int len = column.length();
        final StringBuilder sb = new StringBuilder(len);
        char ch;
        int underLineIndex = -9;
        if (isUpperCase(column)) {
            column = column.toLowerCase();
        }
        for (int i = 0; i < len; i++) {
            ch = column.charAt(i);
            if ('_' == column.charAt(i)) {
                underLineIndex = i;
                continue;
            }
            if (i == 0) {
                sb.append(Character.toUpperCase(ch));
            } else if (i - underLineIndex == 1) {
                sb.append(Character.toUpperCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * 大写驼峰转成小写驼峰：FondsCode => fondsCode
     *
     * @param str 大写驼峰字符
     * @return String
     */
    public static String upperToLowerCamelCase(String str) {
        final StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toLowerCase(str.charAt(0)));
        return sb.toString();
    }
}
