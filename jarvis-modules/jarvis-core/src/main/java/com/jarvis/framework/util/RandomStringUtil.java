package com.jarvis.framework.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年4月29日
 */
public class RandomStringUtil {

    private static final String BASE_CHAR = "0aAbBc1CdDeE2fFgGh3HiIjJ4kKlLm5MnNoO6pPqQr7RsStT8uUvVw9WxXyYzZ";

    private static final int BASE_LEN = BASE_CHAR.length();

    public static String random(int len) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (BASE_CHAR.charAt(random.nextInt(BASE_LEN)));
        }
        return new String(chars);
    }
}
