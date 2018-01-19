package com.diligrp.etrade.shared.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.diligrp.etrade.shared.type.IEnumType;

import java.lang.reflect.Type;

/**
 * FastJson自定义Enum序列化工具
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class EnumTypeSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            serializer.getWriter().writeNull();
            return;
        }

        out.writeInt(((IEnumType) object).getCode());
    }
}
