package com.diligrp.etrade.shared.sequence;

import org.springframework.stereotype.Component;

/**
 * SequenceKey基础类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
@Component("keyGeneratorManager")
public interface IKeyGenerator {
    /**
     * 获取下一个ID
     *
     * @return 下一个ID
     */
    long nextId();
}
