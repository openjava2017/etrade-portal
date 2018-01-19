package com.diligrp.etrade.oauth.session;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Session序列化的ProtocolStuff实现
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class ProtocolStuffSerializer implements ISessionSerializer {
    private Schema<String> keySchema = RuntimeSchema.getSchema(String.class);
    private Schema<SharedHttpSession> sessionSchema = RuntimeSchema.getSchema(SharedHttpSession.class);

    @Override
    public byte[] serializeKey(String key)
    {
        return ProtostuffIOUtil.toByteArray(key, keySchema, LinkedBuffer.allocate(256));
    }

    @Override
    public byte[] serializeSession(SharedHttpSession session)
    {
        return ProtostuffIOUtil.toByteArray(session, sessionSchema, LinkedBuffer.allocate(256));
    }

    @Override
    public SharedHttpSession deserializeSession(byte[] data)
    {
        SharedHttpSession session = new SharedHttpSession();
        ProtostuffIOUtil.mergeFrom(data, session, sessionSchema);
        return session;
    }
}
