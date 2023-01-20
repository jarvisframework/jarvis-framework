package comjarvis.framework.redis.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import comjarvis.framework.redis.builder.StringObjectRedisTemplateBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class StringObjectRedisTemplate extends RedisTemplate<String, Object> {
    public StringObjectRedisTemplate() {
        this.setKeySerializer(RedisSerializer.string());
        this.setHashKeySerializer(RedisSerializer.string());
    }

    public StringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this();
        GenericJackson2JsonRedisSerializer valueSerializer = StringObjectRedisTemplateBuilder.genericJackson2JsonRedisSerializer();
        this.setValueSerializer(valueSerializer);
        this.setHashKeySerializer(valueSerializer);
        this.setConnectionFactory(redisConnectionFactory);
        this.afterPropertiesSet();
    }

    public StringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        this();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        this.setValueSerializer(valueSerializer);
        this.setHashKeySerializer(valueSerializer);
        this.setConnectionFactory(redisConnectionFactory);
        this.afterPropertiesSet();
    }
}
