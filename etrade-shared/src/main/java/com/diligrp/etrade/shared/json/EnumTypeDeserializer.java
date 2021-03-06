package com.diligrp.etrade.shared.json;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.diligrp.etrade.shared.type.IEnumType;

import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * FastJson自定义Enum反序列化工具
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class EnumTypeDeserializer<E extends IEnumType> implements ObjectDeserializer {
    private final Stream<E> enumStream;
    private final String enumName;

    public EnumTypeDeserializer(Class<E> type) {
        enumName = type.getSimpleName();
        if (type == null) {
            throw new JSONException("Type argument cannot be null");
        } else {
            E[] enums = type.getEnumConstants();
            if (enums == null) {
                throw new JSONException(enumName + " does not represent an enum type.");
            }
            enumStream = Stream.of(enums);
        }
    }

    @Override
    public E deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        try {
            Object value;
            final JSONLexer lexer = parser.lexer;
            final int token = lexer.token();
            if (token == JSONToken.LITERAL_INT) {
                int intValue = lexer.intValue();
                lexer.nextToken(JSONToken.COMMA);
                E e = getEnumType(intValue);
                if (e == null) {
                    throw new JSONException("parse enum " + enumName + " error, value : " + lexer.intValue());
                }
                return e;
            } else if (token == JSONToken.LITERAL_STRING) {
                lexer.nextToken(JSONToken.COMMA);
                throw new JSONException("parse enum " + enumName + " error, value : " + lexer.stringVal());
            } else if (token == JSONToken.NULL) {
                lexer.nextToken(JSONToken.COMMA);

                return null;
            } else {
                value = parser.parse();
            }

            throw new JSONException("parse enum " + enumName + " error, value : " + value);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }

    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }

    private E getEnumType(int code) {
        return enumStream.filter(el -> el.getCode() == code).findFirst().orElse(null);
    }
}
