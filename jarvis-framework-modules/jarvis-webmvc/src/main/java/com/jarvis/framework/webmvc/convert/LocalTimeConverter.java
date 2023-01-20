package com.jarvis.framework.webmvc.convert;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements Converter<String, LocalTime> {
    public LocalTimeConverter() {
    }

    public LocalTime convert(String source) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime date = null;

        try {
            date = LocalTime.parse(source, df);
            return date;
        } catch (Exception var5) {
            throw new FrameworkException("日期格式转换出错", var5);
        }
    }
}
