package com.jarvis.framework.webmvc.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.jarvis.framework.webmvc.util.DesensitizationUtil;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 脱敏序列化
 *
 * @author Doug Wang
 * @version 1.0.0 2021年4月16日
 */
public class DesensitizationSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveTypeEnum type;

    private Map<SensitiveTypeEnum, DesensitizationSerializer> serializers;

    public DesensitizationSerializer() {
        serializers = new HashMap<>(16);
        serializers.put(SensitiveTypeEnum.ADDRESS, new DesensitizationSerializer(SensitiveTypeEnum.ADDRESS));
        serializers.put(SensitiveTypeEnum.BANK_CARD, new DesensitizationSerializer(SensitiveTypeEnum.BANK_CARD));
        serializers.put(SensitiveTypeEnum.CAR_NUMBER, new DesensitizationSerializer(SensitiveTypeEnum.CAR_NUMBER));
        serializers.put(SensitiveTypeEnum.CHINESE_NAME, new DesensitizationSerializer(SensitiveTypeEnum.CHINESE_NAME));
        serializers.put(SensitiveTypeEnum.EMAIL, new DesensitizationSerializer(SensitiveTypeEnum.EMAIL));
        serializers.put(SensitiveTypeEnum.FIXED_PHONE, new DesensitizationSerializer(SensitiveTypeEnum.FIXED_PHONE));
        serializers.put(SensitiveTypeEnum.ID_CARD, new DesensitizationSerializer(SensitiveTypeEnum.ID_CARD));
        serializers.put(SensitiveTypeEnum.MOBILE_PHONE, new DesensitizationSerializer(SensitiveTypeEnum.MOBILE_PHONE));
        serializers.put(SensitiveTypeEnum.KEY, new DesensitizationSerializer(SensitiveTypeEnum.KEY));
        serializers.put(SensitiveTypeEnum.PASSWORD, new DesensitizationSerializer(SensitiveTypeEnum.PASSWORD));
    }

    public DesensitizationSerializer(SensitiveTypeEnum type) {
        this.type = type;
    }

    @Override
    public void serialize(final String origin, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        Assert.notNull(type, "SensitiveTypeEnum type should not be null.");
        switch (type) {
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizationUtil.mobilePhone(origin));
                break;
            case PASSWORD:
                jsonGenerator.writeString(DesensitizationUtil.password(origin));
                break;
            case CHINESE_NAME:
                jsonGenerator.writeString(DesensitizationUtil.chineseName(origin));
                break;
            case ID_CARD:
                jsonGenerator.writeString(DesensitizationUtil.idCardNum(origin));
                break;
            case CAR_NUMBER:
                jsonGenerator.writeString(DesensitizationUtil.carNumber(origin));
                break;
            case FIXED_PHONE:
                jsonGenerator.writeString(DesensitizationUtil.fixedPhone(origin));
                break;
            case ADDRESS:
                jsonGenerator.writeString(DesensitizationUtil.address(origin));
                break;
            case EMAIL:
                jsonGenerator.writeString(DesensitizationUtil.email(origin));
                break;
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizationUtil.bankCard(origin));
                break;
            case KEY:
                jsonGenerator.writeString(DesensitizationUtil.key(origin));
                break;
            default:
                throw new IllegalArgumentException("Unknow SensitiveTypeEnum type：" + type);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitization sensitive = beanProperty.getAnnotation(Desensitization.class);
                if (sensitive == null) {
                    sensitive = beanProperty.getContextAnnotation(Desensitization.class);
                }
                if (sensitive != null) {
                    return serializers.get(sensitive.type());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }
}
