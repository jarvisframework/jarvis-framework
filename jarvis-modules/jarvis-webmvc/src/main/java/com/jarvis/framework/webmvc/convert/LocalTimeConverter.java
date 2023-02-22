package com.jarvis.framework.webmvc.convert;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.jarvis.framework.webmvc.constant.DateTimeFormatterPattern.NORM_TIME_PATTERN;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年2月7日
 */
public class LocalTimeConverter implements Converter<String, LocalTime> {

    /**
     *
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public LocalTime convert(String source) {
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(NORM_TIME_PATTERN);
        LocalTime date = null;
        try {
            date = LocalTime.parse((String) source, df);
        } catch (final Exception e) {
            throw new FrameworkException("日期格式转换出错", e);
        }
        return date;
    }

}
