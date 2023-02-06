package com.jarvis.framework.webmvc.convert;

import com.jarvis.framework.core.exception.FrameworkException;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.jarvis.framework.webmvc.constant.DateTimeFormatterPattern.NORM_DATETIME_PATTERN;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年2月7日
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    /**
     *
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public LocalDateTime convert(String source) {
        final DateTimeFormatter df = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);
        LocalDateTime date = null;
        try {
            date = LocalDateTime.parse((String) source, df);
        } catch (final Exception e) {
            throw new FrameworkException("日期格式转换出错", e);
        }
        return date;
    }

}
