package com.diligrp.etrade.shared.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.diligrp.etrade.shared.type.IEnumType;
import com.diligrp.etrade.shared.util.ClassUtils;

/**
 * FastJson工具类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public class JsonUtils {

    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteDateUseDateFormat);
    }

    public static <T> T fromJsonString(String json, Class<T> type) {
        return JSON.parseObject(json, type);
    }

    public static void registerSerializeConfig(String name) {
        try {
            SerializeConfig config = SerializeConfig.getGlobalInstance();
            Class<?> type = ClassUtils.getDefaultClassLoader().loadClass(name);
            config.put(type, new EnumTypeSerializer());
        } catch (Exception ex) {
            throw new RuntimeException("FastJson serialize config init exception", ex);
        }
    }

    public static void registerParserConfig(String name) {
        try {
            ParserConfig config = ParserConfig.getGlobalInstance();
            Class<? extends IEnumType> type =
                    (Class<? extends IEnumType>) ClassUtils.getDefaultClassLoader().loadClass(name);
            config.putDeserializer(type, new EnumTypeDeserializer<>(type));
        } catch (Exception ex) {
            throw new RuntimeException("FastJson parser config init exception", ex);
        }
    }
}
