package com.diligrp.etrade.oauth.session;

/**
 * Session序列化接口
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public interface ISessionSerializer {
    /**
     * 序列化字符串
     *
     * @param key - 待序列化的字符串
     * @return 序列化后的字节流
     */
    byte[] serializeKey(String key);

    /**
     * 序列化用户Session
     *
     * @param session - 待序列化的Session
     * @return 序列化后的字节流
     */
    byte[] serializeSession(SharedHttpSession session);

    /**
     * 根据字节流反序列化得到用户Session
     *
     * @param data - 用户Session字节流
     * @return 用户Session
     */
    SharedHttpSession deserializeSession(byte[] data);
}
