package com.jarvis.framework.webmvc.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * JavaScript能表示并进行精确算术运算的整数范围为：正负2的53次方，也即从最小值-9007199254740992到最大值+9007199254740992之间的范围；对于超过这个范围的整数转成stirng类型
 *
 * @author qiucs
 * @version 1.0.0 2019年9月12日
 */
public class LongToStringSerializer extends StdSerializer<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -381175140840848832L;

    /**
     * Singleton instance to use.
     */
    public final static LongToStringSerializer INSTANCE = new LongToStringSerializer();

    /**
     * <p>
     * Note: usually you should NOT create new instances, but instead use
     * {@link #INSTANCE} which is stateless and fully thread-safe. However,
     * there are cases where constructor is needed; for example,
     * when using explicit serializer annotations like
     * {@link com.fasterxml.jackson.databind.annotation.JsonSerialize#using}.
     */
    public LongToStringSerializer() {
        super(Long.class);
    }

    /**
     * Sometimes it may actually make sense to retain actual handled type, so...
     *
     * @since 2.5
     */
    public LongToStringSerializer(Class<?> handledType) {
        super(handledType, false);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Long value) {
        return value.toString().isEmpty();
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        /*if (value > 9007199254740992L || value < -9007199254740992L) {
        } else {
            gen.writeNumber(value);
        }*/
        gen.writeString(value.toString());
    }

    /* 01-Mar-2011, tatu: We were serializing as "raw" String; but generally that
     *   is not what we want, since lack of type information would imply real
     *   String type.
     */
    /**
     * Default implementation will write type prefix, call regular serialization
     * method (since assumption is that value itself does not need JSON
     * Array or Object start/end markers), and then write type suffix.
     * This should work for most cases; some sub-classes may want to
     * change this behavior.
     */
    @Override
    public void serializeWithType(Long value, JsonGenerator g, SerializerProvider provider,
                                  TypeSerializer typeSer)
            throws IOException {
        final WritableTypeId typeIdDef = typeSer.writeTypePrefix(g,
                typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, g, provider);
        typeSer.writeTypeSuffix(g, typeIdDef);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return createSchemaNode("string", true);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
            throws JsonMappingException {
        visitStringFormat(visitor, typeHint);
    }
}
