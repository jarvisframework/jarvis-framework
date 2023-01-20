package com.yx.framework.tool.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yx.framework.tool.constant.DateTimePatternConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * <p>Json工具类（针对Jackson的二次封装）</p>
 * <p>
 * 对比Java json框架,Jackson对于json的序列化和反序列化性能都是最好的，
 * 但是使用复杂度相对较高，故对Jackson进行二次封装
 *
 * @author 王涛
 * @since 1.0, 2020-12-14 18:35:52
 */
public abstract class JacksonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimePatternConstants.LONG_DATE_PATTERN_LINE));
        // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将bean序列化为json
     *
     * @param bean 序列化对象
     * @return json字符串
     */
    public static <T> String beanToJson(T bean) {
        if (null == bean) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将json字符串反序列化为bean
     *
     * @param jsonStr json字符串
     * @param clazz   bean类型
     * @return 反序列化bean
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr) || null == clazz) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将json数组字符串反序列化为对象集合
     *
     * @param jsonArrayStr    json数组字符串
     * @param collectionClazz 集合容器类型
     * @param elementClazz    bean节点类型
     * @param <T>             bean节点类型
     * @return 对象集合
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<?> collectionClazz, Class<?>... elementClazz) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClazz, elementClazz);
        try {
            return OBJECT_MAPPER.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * Map转JavaBean
     *
     * @param map   需转换为bean的map
     * @param clazz bean类型
     * @param <T>   bean类型
     * @return 转换后的bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }


    /**
     * 判断字符串是否为JSON
     *
     * @param jsonStr 需判断的JSON字符串
     * @return 判断结果
     */
    public static boolean isJson(String jsonStr) {
        if (StringUtils.isBlank(jsonStr)) {
            return false;
        }
        try {
            OBJECT_MAPPER.readValue(jsonStr, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
