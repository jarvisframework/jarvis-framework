package com.yx.framework.tool.util;

import com.yx.framework.tool.constant.DateTimePatternConstants;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtilsTest {


    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String dateStr = DateUtils.format(new Date(), DateTimePatternConstants.LONG_DATE_PATTERN_LINE);
                System.out.println(dateStr);
                System.out.println("------------------------");
                Date date = DateUtils.parse(dateStr);
                System.out.println(date);
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void yearStartTest() {
        System.out.println(ChronoIntervalUtils.yearStart(new Date()));
    }

    @Test
    public void timeUnitTest() {
        long seconds = TimeUnit.MINUTES.toHours(10);
        System.out.println(seconds);
    }


}