package com.jarvis.framework.constant;

import com.jarvis.framework.util.RandomStringUtil;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年5月25日
 */
public abstract class WebMvcConstant {

    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    public final static String PERMIT_HEADER_NAME = "X-Request-Access-Id";

    public final static String FEIGN_HEADER_NAME = "X-Feign-Access-Request";

    public final static String MENU_HEADER_NAME = "X-Menu-Id";

    public final static String SYSTEM_HEADER_NAME = "X-System-Id";

    public final static String PERMIT_HEADER_VALUE = RandomStringUtil.random(8);
}
