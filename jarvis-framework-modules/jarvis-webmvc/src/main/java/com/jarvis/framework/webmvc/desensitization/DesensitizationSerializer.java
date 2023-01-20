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

public class DesensitizationSerializer extends JsonSerializer<String> implements ContextualSerializer {
    private SensitiveTypeEnum type;
    private Map<SensitiveTypeEnum, DesensitizationSerializer> serializers;

    public DesensitizationSerializer() {
        this.serializers = new HashMap(16);
        this.serializers.put(SensitiveTypeEnum.ADDRESS, new DesensitizationSerializer(SensitiveTypeEnum.ADDRESS));
        this.serializers.put(SensitiveTypeEnum.BANK_CARD, new DesensitizationSerializer(SensitiveTypeEnum.BANK_CARD));
        this.serializers.put(SensitiveTypeEnum.CAR_NUMBER, new DesensitizationSerializer(SensitiveTypeEnum.CAR_NUMBER));
        this.serializers.put(SensitiveTypeEnum.CHINESE_NAME, new DesensitizationSerializer(SensitiveTypeEnum.CHINESE_NAME));
        this.serializers.put(SensitiveTypeEnum.EMAIL, new DesensitizationSerializer(SensitiveTypeEnum.EMAIL));
        this.serializers.put(SensitiveTypeEnum.FIXED_PHONE, new DesensitizationSerializer(SensitiveTypeEnum.FIXED_PHONE));
        this.serializers.put(SensitiveTypeEnum.ID_CARD, new DesensitizationSerializer(SensitiveTypeEnum.ID_CARD));
        this.serializers.put(SensitiveTypeEnum.MOBILE_PHONE, new DesensitizationSerializer(SensitiveTypeEnum.MOBILE_PHONE));
        this.serializers.put(SensitiveTypeEnum.KEY, new DesensitizationSerializer(SensitiveTypeEnum.KEY));
        this.serializers.put(SensitiveTypeEnum.PASSWORD, new DesensitizationSerializer(SensitiveTypeEnum.PASSWORD));
    }

    public DesensitizationSerializer(SensitiveTypeEnum type) {
        this.type = type;
    }

    public void serialize(String origin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Assert.notNull(this.type, "SensitiveTypeEnum type should not be null.");
        switch(this.type) {
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
            throw new IllegalArgumentException("Unknow SensitiveTypeEnum type：" + this.type);
        }

    }

    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitization sensitive = (Desensitization)beanProperty.getAnnotation(Desensitization.class);
                if (sensitive == null) {
                    sensitive = (Desensitization)beanProperty.getContextAnnotation(Desensitization.class);
                }

                if (sensitive != null) {
                    return (JsonSerializer)this.serializers.get(sensitive.type());
                }
            }

            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        } else {
            return serializerProvider.findNullValueSerializer((BeanProperty)null);
        }
    }
}
