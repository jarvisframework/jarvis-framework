package com.jarvis.framework.util;

public abstract class ServiceNameUtil {

    private static final String HOST_OWNER_KEY = "HOST_OWNER";

    public static final String HOST_OWNER = "${HOST_OWNER:}";

    public static String hostOwner() {
        String hostOwner = System.getenv("HOST_OWNER");
        return null == hostOwner ? "" : hostOwner;
    }
}
