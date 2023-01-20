package com.yx.framework.tool.util;

import com.yx.framework.tool.constant.SecondUnitConstants;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>时差工具类</p>
 *
 * @author 王涛
 * @since 1.0, 2020-12-14 17:27:48
 */
public abstract class ChronoIntervalUtils {

    /**
     * 将正在运行Java虚拟机的当天开始时间对象返回
     *
     * @return 当天开始时间对象
     */
    public static Date todayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的年开始
     *
     * @param date 指定日期对象
     * @return 指定日期年开始对象
     */
    public static String yearStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return String.format("%d-01-01 00:00:00", year);
    }

    /**
     * 获取指定日期的年结尾
     *
     * @param date 指定日期对象
     * @return 指定日期年开始结尾
     */
    public static String yearEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return String.format("%d-12-31 23:59:00", year);
    }

    /**
     * 获取两日期的间隔天数
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return 间隔天数
     */
    public static int intervalDays(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()) / SecondUnitConstants.DAY_MILLI);
    }

    /**
     * 获取两日期的间隔小时
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return 间隔小时
     */
    public static long intervalHours(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()) / SecondUnitConstants.HOUR_MILLI);
    }

    /**
     * 获取两个日期的间隔分钟数
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return 间隔分
     */
    public static int intervalMinutes(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()) / SecondUnitConstants.MINUTE_MILLI);
    }

    /**
     * 获取两个日期的间隔秒数
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return 间隔秒
     */
    public static long intervalSeconds(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()) / SecondUnitConstants.SECOND_MILLI);
    }

    /**
     * 获取日期的间隔毫秒数
     *
     * @param firstDate  第一个日期
     * @param secondDate 第二个日期
     * @return 间隔毫秒
     */
    public static int intervalMilliSecond(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()));
    }

}
