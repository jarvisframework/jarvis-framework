package com.jarvis.framework.util;

import java.text.DecimalFormat;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年12月15日
 */
public class FileUtil {

    /**
     * 文件大小转换 "B", "KB", "MB", "GB", "TB"
     * 会将文件大小转换为最大满足单位
     *
     * @param size（文件大小，单位为B）
     * @return String
     */
    public static String readableSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        final int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,###.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
