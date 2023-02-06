package comjarvis.framework.redis.builder;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import comjarvis.framework.redis.core.StringObjectRedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RedisTemplate<String, Object>对象构造器
 *
 * @author qiucs
 * @version 1.0.0 2021年5月12日
 */
public abstract class StringObjectRedisTemplateBuilder {

    public static RedisTemplate<String, Object> build(RedisConnectionFactory redisConnectionFactory) {
        return new StringObjectRedisTemplate(redisConnectionFactory);
    }

    public static RedisTemplate<String, Object> build(RedisConnectionFactory redisConnectionFactory,
                                                      ObjectMapper objectMapper) {
        return new StringObjectRedisTemplate(redisConnectionFactory, objectMapper);
    }

    /**
     * 使用GenericJackson2JsonRedisSerializer
     *
     * @return GenericJackson2JsonRedisSerializer
     */
    public static GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(jsonRedisSerializerMapper());
    }

    /**
     * 使用GenericJackson2JsonRedisSerializer的ObjectMapper对象
     *
     * @return ObjectMapper
     */
    public static ObjectMapper jsonRedisSerializerMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dtf));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dtf));
        objectMapper.registerModule(javaTimeModule);

        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
