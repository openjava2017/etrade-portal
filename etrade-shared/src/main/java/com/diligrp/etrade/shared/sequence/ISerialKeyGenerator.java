package com.diligrp.etrade.shared.sequence;

/**
 * SerialKey基础类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public interface ISerialKeyGenerator {
    /**
     * 获取下一个序列号
     *
     * @param typeCode - SerialKey类型码
     * @param scope - 使用范围
     * @return 下一个序列号
     */
    String nextSerialNo(String typeCode, String scope);
}
