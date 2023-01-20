package com.jarvis.framework.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStringUtil {

    private static final String BASE_CHAR = "0aAbBc1CdDeE2fFgGh3HiIjJ4kKlLm5MnNoO6pPqQr7RsStT8uUvVw9WxXyYzZ";

    private static final int BASE_LEN = "0aAbBc1CdDeE2fFgGh3HiIjJ4kKlLm5MnNoO6pPqQr7RsStT8uUvVw9WxXyYzZ".length();

    public RandomStringUtil() {
    }

    public static String random(int len) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] chars = new char[len];

        for(int i = 0; i < len; ++i) {
            chars[i] = "0aAbBc1CdDeE2fFgGh3HiIjJ4kKlLm5MnNoO6pPqQr7RsStT8uUvVw9WxXyYzZ".charAt(random.nextInt(BASE_LEN));
        }

        return new String(chars);
    }
}
