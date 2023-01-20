package com.yx.framework.tool.constant;

/**
 * <p>秒单位常量</p>
 *
 * @author 王涛
 * @since 1.0, 2020-12-14 14:50:04
 */
public abstract class SecondUnitConstants {

    /**
     * 秒
     */
    public static final Integer SECOND_MILLI = 1000;
    /**
     * 分钟
     */
    public static final Integer MINUTE_MILLI = 60 * SECOND_MILLI;
    /**
     * 小时
     */
    public static final Integer HOUR_MILLI = 60 * MINUTE_MILLI;
    /**
     * 天
     */
    public static final Integer DAY_MILLI = 24 * HOUR_MILLI;
    /**
     * 月
     */
    public static final Integer MONTH_MILLI = 31 * DAY_MILLI;
    /**
     * 年
     */
    public static final Integer YEAR_MILLI = 12 * DAY_MILLI;

}
