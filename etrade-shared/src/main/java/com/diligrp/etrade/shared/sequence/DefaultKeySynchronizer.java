package com.diligrp.etrade.shared.sequence;

import com.diligrp.etrade.shared.dao.ISequenceKeyDao;
import com.diligrp.etrade.shared.domain.PersistentSequenceKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * SequenceKey数据同步的实现类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
@Service("keySynchronizer")
public class DefaultKeySynchronizer implements IKeySynchronizer {

    @Resource
    private ISequenceKeyDao sequenceKeyDao;

    /**
     * 加载指定的SequenceKey
     *
     * @param key   - SequenceKey的唯一标识
     * @param scope - 使用范围
     * @return SequenceKey
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public PersistentSequenceKey loadSequenceKey(String key, String scope) {
        if (scope == null) {
            return sequenceKeyDao.loadSequenceKey(key);
        } else {
            Map<String, Object> params = new HashMap<>(2);
            params.put("key", key);
            params.put("scope", scope);
            return sequenceKeyDao.loadScopeSequenceKey(params);
        }
    }

    /**
     * 获取指定的SequenceKey的起始值
     *
     * @param id - SequenceKey的数据库主键
     * @return SequenceKey的起始值
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Long getSequenceKeyValue(Long id) {
        return sequenceKeyDao.getSequenceKeyValue(id);
    }

    /**
     * 比较并设置某个SequenceKey的起始值，数据库乐观锁简易实现
     *
     * @param id       - SequenceKey的数据库主键
     * @param oldValue - SequenceKey的起始值的原值
     * @param newValue - SequenceKey的起始值的新值
     * @return true-设置成功，false-设置失败
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean compareAndSet(Long id, Long oldValue, Long newValue) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("id", id);
        params.put("oldValue", oldValue);
        params.put("newValue", newValue);
        return sequenceKeyDao.compareAndSet(params) > 0;
    }
}
