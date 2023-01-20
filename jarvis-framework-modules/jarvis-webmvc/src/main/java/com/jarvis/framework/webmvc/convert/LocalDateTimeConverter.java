package com.jarvis.framework.webmvc.convert;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    public LocalDateTime convert(String source) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = null;

        try {
            date = LocalDateTime.parse(source, df);
            return date;
        } catch (Exception var5) {
            throw new FrameworkException("日期格式转换出错", var5);
        }
    }
}
