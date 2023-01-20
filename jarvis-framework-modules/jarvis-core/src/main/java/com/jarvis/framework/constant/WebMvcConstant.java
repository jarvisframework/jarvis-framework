package com.jarvis.framework.constant;

import com.jarvis.framework.util.RandomStringUtil;

public abstract class WebMvcConstant {
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    public static final String PERMIT_HEADER_NAME = "X-Request-Access-Id";
    public static final String FEIGN_HEADER_NAME = "X-Feign-Access-Request";
    public static final String MENU_HEADER_NAME = "X-Menu-Id";
    public static final String SYSTEM_HEADER_NAME = "X-System-Id";
    public static final String PERMIT_HEADER_VALUE = RandomStringUtil.random(8);
}
