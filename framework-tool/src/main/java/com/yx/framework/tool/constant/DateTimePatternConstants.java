package com.yx.framework.tool.constant;

/**
 * <p>日期时间模式常量类</p>
 *
 * @author 王涛
 * @since 1.0, 2020-12-14 13:56:09
 */
public abstract class DateTimePatternConstants {

    /**
     * 分钟格式（M）
     */
    public static final String TIME_M_PATTERN = "M";

    /**
     * 小时数格式（24H）
     */
    public static final String TIME_H_PATTERN = "H";

    /**
     * 天数格式（D）
     */
    public static final String DATE_D_PATTERN = "D";

    /**
     * 时分秒格式（HH:mm:ss）
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 年格式（yyyy）
     */
    public static final String YEAR_PATTERN = "yyyy";

    // -----------------------------短日期格式-----------------------------

    /**
     * 短日期格式（yyyy-MM-dd）
     */
    public static final String SHORT_DATE_PATTERN_LINE = "yyyy-MM-dd";

    /**
     * 短日期格式（yyyy/MM/dd）
     */
    public static final String SHORT_DATE_PATTERN_SLASH = "yyyy/MM/dd";

    /**
     * 短日期格式（yyyy\MM\dd）
     */
    public static final String SHORT_DATE_PATTERN_BACKSLASH = "yyyy\\MM\\dd";

    /**
     * 短日期格式（yyyy年MM月dd日）
     */
    public static final String SHORT_DATE_PATTERN_CN = "yyyy年MM月dd日";

    /**
     * 短日期格式（yyyyMMdd）
     */
    public static final String SHORT_DATE_PATTERN_NONE = "yyyyMMdd";

    // -----------------------------长日期格式-----------------------------

    /**
     * 长日期格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String LONG_DATE_PATTERN_LINE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 长日期格式(yyyy/MM/dd HH:mm:ss)
     */
    public static final String LONG_DATE_PATTERN_SLASH = "yyyy/MM/dd HH:mm:ss";

    /**
     * 长日期格式(yyyy\MM\dd HH:mm:ss)
     */
    public static final String LONG_DATE_PATTERN_BACKSLASH = "yyyy\\MM\\dd HH:mm:ss";

    /**
     * 长日期格式(yyyy年MM月dd日 HH时mm分ss秒)
     */
    public static final String LONG_DATE_PATTERN_CN = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 长日期格式(YYYY-MM-DD HH24MISS)
     */
    public static final String LONG_DATE_PATTERN_ORACLE = "YYYY-MM-DD HH24MISS";

    /**
     * 长日期格式(yyyyMMdd HH:mm:ss)
     */
    public static final String LONG_DATE_PATTERN_NONE = "yyyyMMdd HH:mm:ss";

    // -----------------------------长日期时间格式 带毫秒-----------------------------

    /**
     * 长日期时间格式 带毫秒(yyyy-MM-dd HH:mm:ss.SSS)
     */
    public static final String LONG_DATE_PATTERN_WITH_MILLISECOND_LINE = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 长日期时间格式 带毫秒(yyyy/MM/dd HH:mm:ss.SSS)
     */
    public static final String LONG_DATE_PATTERN_WITH_MILLISECOND_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

    /**
     * 长日期时间格式 带毫秒(yyyy\MM\dd HH:mm:ss.SSS)
     */
    public static final String LONG_DATE_PATTERN_WITH_MILLISECOND_BACKSLASH = "yyyy\\MM\\dd HH:mm:ss.SSS";

    /**
     * 长日期时间格式 带毫秒(yyyyMMdd HH:mm:ss.SSS)
     */
    public static final String LONG_DATE_PATTERN_WITH_MILLISECOND_NONE = "yyyyMMdd HH:mm:ss.SSS";

}
