package com.diligrp.etrade.shared.sequence;

/**
 * SequenceKey基础类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public interface IKeyGenerator {
    /**
     * 获取下一个ID
     *
     * @return 下一个ID
     */
    long nextId();
}
