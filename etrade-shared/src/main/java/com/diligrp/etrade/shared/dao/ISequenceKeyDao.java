package com.diligrp.etrade.shared.dao;

import com.diligrp.etrade.shared.domain.PersistentSequenceKey;
import com.diligrp.etrade.shared.mybatis.MybatisMapperSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * KeySequence数据操作
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
@Repository("sequenceKeyDao")
public interface ISequenceKeyDao extends MybatisMapperSupport {
    /**
     * 加载指定的SequenceKey
     *
     * @param key - SequenceKey的唯一标识
     * @return SequenceKey
     */
    PersistentSequenceKey loadSequenceKey(String key);

    /**
     * 加载指定的SequenceKey
     *
     * @param params - 参数列表：key/scope
     * @return SequenceKey
     */
    PersistentSequenceKey loadScopeSequenceKey(Map<String, Object> params);

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
     * @param params - 参数列表：id/oldVersion/newVersion
     * @return 1-更新成功，0-更新失败
     */
    int compareAndSet(Map<String, Object> params);
}
