package com.funicorn.basic.common.base.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author Aimee
 * @since 2020/6/20 18:40
 */
@Slf4j
@SuppressWarnings("unused")
public class JsonUtil {

    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();
    public static final String ERROR_MSG = "将 Json 转换为对象时异常,数据是:";
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat(PATTERN));
        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(PATTERN)));
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(PATTERN)));
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    public static String object2Json(Object o) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;

        try {
            gen = (new JsonFactory()).createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, o);
        } catch (IOException var11) {
            throw new RuntimeException("不能序列化对象为Json", var11);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException var10) {
                    log.error("JsonGenerator关闭异常", var10);
                }
            }

        }

        return sw.toString();
    }

    public static Map<?,?> object2Map(Object o) {
        return o == null ? null : OBJECT_MAPPER.convertValue(o, Map.class);
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException var3) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, var3);
        }
    }

    public static <T> T object2Object(Object object, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(object2Json(object), clazz);
        } catch (IOException var3) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + object2Json(object), var3);
        }
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        CollectionType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);

        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException var4) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, var4);
        }
    }

    public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException var3) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), var3);
        }
    }

    public static JsonNode object2Node(Object o) {
        try {
            return (o == null ? OBJECT_MAPPER.createObjectNode() : OBJECT_MAPPER.convertValue(o, JsonNode.class));
        } catch (Exception var2) {
            throw new RuntimeException("不能序列化对象为Json", var2);
        }
    }

}
