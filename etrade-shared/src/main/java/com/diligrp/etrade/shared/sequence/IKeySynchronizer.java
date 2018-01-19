package com.diligrp.etrade.shared.sequence;

import com.diligrp.etrade.shared.domain.PersistentSequenceKey;

/**
 * SequenceKey数据同步基础类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public interface IKeySynchronizer {
    /**
     * 加载指定的SequenceKey
     *
     * @param key - SequenceKey的唯一标识
     * @param scope - 使用范围
     * @return SequenceKey
     */
    PersistentSequenceKey loadSequenceKey(String key, String scope);

    /**
     * 获取指定的SequenceKey的起始值
     *
     * @param id - SequenceKey的数据库主键
     * @return SequenceKey的起始值
     */
    Long getSequenceKeyValue(Long id);

    /**
     * 比较并设置某个SequenceKey的起始值，数据库乐观锁简易实现
     *
     * @param id - SequenceKey的数据库主键
     * @param oldValue - SequenceKey的起始值的原值
     * @param newValue - SequenceKey的起始值的新值
     * @return true-设置成功，false-设置失败
     */
    boolean compareAndSet(Long id, Long oldValue, Long newValue);
}
