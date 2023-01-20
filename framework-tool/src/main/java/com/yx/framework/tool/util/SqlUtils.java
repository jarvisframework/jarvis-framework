package com.yx.framework.tool.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>SQL工具类</p>
 *
 * @author 王涛
 * @since 1.0, 2021-06-03 13:48:54
 */
public abstract class SqlUtils {

    /**
     * 返回字符型ids数组的in语句
     *
     * @param ids 字符串数组
     * @return in语句
     */
    public static String inStatementOf(String[] ids) {
        return String.format("'%s'", String.join("','", Arrays.stream(ids).distinct().collect(Collectors.toList())));
    }

    /**
     * 返回字符型ids集合的in语句
     *
     * @param ids 字符串集合
     * @return in语句
     */
    public static String inStatementOf(List<String> ids) {
        return String.format("'%s'", String.join("','", ids.stream().distinct().collect(Collectors.toList())));
    }

    /**
     * 返回整数型ids集合的in语句
     *
     * @param ids 整数型数组
     * @return in语句
     */
    public static String inStatementOf(int[] ids) {
        List<String> idList = new ArrayList<>(ids.length);
        Arrays.stream(ids).distinct().forEach(e -> {
            idList.add(String.valueOf(e));
        });
        return String.join(",", idList);
    }
}
