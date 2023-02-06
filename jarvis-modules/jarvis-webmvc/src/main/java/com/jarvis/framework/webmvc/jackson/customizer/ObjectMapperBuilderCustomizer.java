package com.jarvis.framework.webmvc.jackson.customizer;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.jarvis.framework.webmvc.jackson.module.LongToStringModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.jarvis.framework.webmvc.constant.DateTimeFormatterPattern.NORM_DATETIME_PATTERN;
import static com.jarvis.framework.webmvc.constant.DateTimeFormatterPattern.NORM_DATE_PATTERN;
import static com.jarvis.framework.webmvc.constant.DateTimeFormatterPattern.NORM_TIME_PATTERN;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年2月8日
 */
public class ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    /**
     *
     * @see org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer#customize(org.springframework.http.converter.json.Jackson2ObjectMapperBuilder)
     */
    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.modules(javaTimeModule(), new LongToStringModule());
    }

    private JavaTimeModule javaTimeModule() {
        final JavaTimeModule module = new JavaTimeModule();
        // 序列化
        module.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
        module.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));

        // 反序列化
        module.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN)));
        module.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN)));
        module.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(NORM_TIME_PATTERN)));
        return module;
    }

}
