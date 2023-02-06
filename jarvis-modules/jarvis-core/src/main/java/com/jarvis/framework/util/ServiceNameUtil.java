package com.jarvis.framework.util;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月14日
 */
public abstract class ServiceNameUtil {

    private static final String HOST_OWNER_KEY = "HOST_OWNER";

    /** 服务名称后缀 */
    public static final String HOST_OWNER = "${" + HOST_OWNER_KEY + ":}";

    /**
     * 从环境变量中获取HOST_OWNER值
     *
     * @return String
     */
    public static String hostOwner() {
        final String hostOwner = System.getenv(HOST_OWNER_KEY);
        if (null == hostOwner) {
            return "";
        }
        return hostOwner;
    }

}
