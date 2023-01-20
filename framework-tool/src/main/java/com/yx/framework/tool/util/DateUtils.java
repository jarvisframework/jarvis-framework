package com.yx.framework.tool.util;

import com.yx.framework.tool.constant.DateTimePatternConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * <p>日期时间工具类</p>
 *
 * @author 王涛
 * @since 1.0, 2020-12-14 13:50:56
 */
public abstract class DateUtils {

    /**
     * 默认格式化模式（yyyy-MM-dd HH:mm:ss）
     */
    private static final String DEFAULT_FORMAT_PATTERN = DateTimePatternConstants.LONG_DATE_PATTERN_LINE;

    /**
     * 加锁安全日期格式化对象
     */
    private static final ThreadLocal<DateFormat> LOCAL = new ThreadLocal<>();

    /**
     * 将日期字符串按默认模式（yyyy-MM-dd HH:mm:ss）解析为日期对象
     *
     * @param dateStr 要解析为日期对象的字符串
     * @return 返回从字符串解析的日期对象
     * @throws RuntimeException 如果无法解析指定字符串的开头
     */
    public static Date parse(String dateStr) throws RuntimeException {
        return parse(dateStr, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 将指定模式的日期字符串解析为日期对象
     *
     * @param dateStr 要解析为日期对象的字符串
     * @param pattern 描述日期和时间格式的模式
     * @return 返回从字符串解析的日期对象
     * @throws RuntimeException 如果无法解析指定字符串的开头
     */
    public static Date parse(String dateStr, String pattern) throws RuntimeException {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat formatter = LOCAL.get();
        if (!Optional.ofNullable(formatter).isPresent()) {
            formatter = new SimpleDateFormat(pattern);
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将日期按默认模式（yyyy-MM-dd HH:mm:ss）格式化为日期/时间字符串
     *
     * @param date 要格式化为时间字符串的时间值
     * @return 格式化的时间字符串
     */
    public static String format(Date date) {
        return format(date, DEFAULT_FORMAT_PATTERN);
    }

    /**
     * 将日期格式化为日期/时间字符串
     *
     * @param date    要格式化为时间字符串的时间值
     * @param pattern 描述日期和时间格式的模式
     * @return 格式化的时间字符串
     */
    public static String format(Date date, String pattern) {
        if (null == date || StringUtils.isBlank(pattern)) {
            return null;
        }
        DateFormat formatter = LOCAL.get();
        if (!Optional.ofNullable(formatter).isPresent()) {
            formatter = new SimpleDateFormat(pattern);
        }
        return formatter.format(date);
    }

    /**
     * 将正在运行Java虚拟机的日期值按默认模式（yyyy-MM-dd HH:mm:ss）格式化返回
     *
     * @return 格式化的时间字符串
     */
    public static String currentDateTimeString() {
        return format(new Date(), DEFAULT_FORMAT_PATTERN);
    }
}
