package com.diligrp.etrade.shared.sequence;

import com.diligrp.etrade.shared.exception.RedisSystemException;
import com.diligrp.etrade.shared.domain.PersistentSequenceKey;
import com.diligrp.etrade.shared.redis.IRedisSystemService;
import com.diligrp.etrade.shared.util.AssertUtils;
import com.diligrp.etrade.shared.util.DateUtils;
import com.diligrp.etrade.shared.util.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SequenceKey和SerialKey管理器实现
 *
 * SequenceKey分布式实现方案：每个服务器节点第一使用SequenceKey时将向数据库请求一组ID（数据库中SequenceKey的startWith=startWith + incSpan）
 * 将SequenceKey缓存在JVM内存中，在使用完这组ID后重新向数据库申请；由于通过数据库乐观锁实现因此每个节点申请到的ID不会重复。
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
@Component("keyGeneratorManager")
public class KeyGeneratorManager {
    private static Logger LOG = LoggerFactory.getLogger(KeyGeneratorManager.class);
    private static final String SEQUENCEID_PREFIX = "application:sequence:";

    private final ConcurrentMap<KeyEntry, IKeyGenerator> keyGenerators = new ConcurrentHashMap<>();
    private final ISerialKeyGenerator keyGenerator = new SerialKeyGeneratorImpl();
    private Lock locker = new ReentrantLock();

    @Resource
    private IKeySynchronizer keySynchronizer;

    @Resource
    private IRedisSystemService redisSystemService;

    public IKeyGenerator getKeyGenerator(SequenceKey key) {
        return getKeyGenerator(key, null);
    }

    public IKeyGenerator getKeyGenerator(SequenceKey key, String scope) {
        if (key == null) {
            throw new IllegalArgumentException("Miss key parameter");
        }

        KeyEntry cachedKey = new KeyEntry(key, scope);
        IKeyGenerator keyGenerator = keyGenerators.get(cachedKey);
        // First check, no need synchronize code block
        if (keyGenerator == null) {
            boolean result = false;
            try {
                result = locker.tryLock(15, TimeUnit.SECONDS);
                if (result) {
                    // Double check for performance purpose
                    if ((keyGenerator = keyGenerators.get(cachedKey)) == null) {
                        int retry = 1;
                        for (; ; retry++) { // CAS
                            PersistentSequenceKey persistentKey = keySynchronizer.loadSequenceKey(key.identifier(), scope);
                            if (persistentKey == null) {
                                throw new RuntimeException("Unregistered sequence key generator: " + key.identifier());
                            }

                            long newStartWith = persistentKey.getStartWith();
                            long newEndWith = newStartWith + persistentKey.getIncSpan();
                            if (keySynchronizer.compareAndSet(persistentKey.getId(), newStartWith, newEndWith)) {
                                keyGenerator = new KeyGeneratorImpl(persistentKey.getId(), cachedKey, newStartWith,
                                        newEndWith - 1, persistentKey.getIncSpan());
                                break;
                            }
                            if (retry >= 8) {
                                throw new RuntimeException("Exceed max retry to generate key id for " + key.identifier());
                            }
                        }
                        keyGenerators.put(cachedKey, keyGenerator);
                    }
                }
            } catch (InterruptedException iex) {
                LOG.error("Thread interrupted", iex);
            } finally {
                if (result) {
                    locker.unlock();
                } else {
                    if (!Thread.interrupted()) {
                        throw new RuntimeException("Timeout to get KeyGenerator for " + key.identifier());
                    }
                }
            }
        }

        return keyGenerator;
    }

    public ISerialKeyGenerator getSerialKeyGenerator() {
        return keyGenerator;
    }

    private class KeyGeneratorImpl implements IKeyGenerator {
        private long id;
        private KeyEntry keyEntry;
        private long startWith;
        private long endWith;
        private long incSpan;
        private Lock keyLocker = new ReentrantLock();

        public KeyGeneratorImpl(long id, KeyEntry key, long startWith, long endWith, long incSpan) {
            this.id = id;
            this.keyEntry = key;
            this.startWith = startWith;
            this.endWith = endWith;
            this.incSpan = incSpan;
        }

        @Override
        public long nextId() {
            boolean result = false;
            try {
                result = keyLocker.tryLock(15L, TimeUnit.SECONDS);
                if (result) {
                    if (startWith <= endWith) {
                        return startWith++;
                    } else {
                        int retry = 1;
                        for (; ; retry++) {
                            long newStartWith = keySynchronizer.getSequenceKeyValue(id);
                            long newEndWith = newStartWith + incSpan;
                            if (keySynchronizer.compareAndSet(id, newStartWith, newEndWith)) {
                                startWith = newStartWith;
                                endWith = newEndWith - 1;
                                break;
                            }
                            if (retry >= 8) {
                                throw new RuntimeException("Exceed max retry to generate key id for " + keyEntry.key.identifier());
                            }
                        }
                        // Then recursive call for a next ID
                        return nextId();
                    }
                } else {
                    throw new RuntimeException("Timeout to generate key id for " + keyEntry.key.identifier());
                }
            } catch (InterruptedException iex) {
                LOG.error("Thread interrupted", iex);
            } finally {
                if (result) {
                    keyLocker.unlock();
                }
            }

            return 0L;
        }
    }

    private class SerialKeyGeneratorImpl implements ISerialKeyGenerator {
        private static final int TEN = 10;

        private static final int HUNDRED = 100;

        private static final int THOUSAND = 1000;

        @Override
        public String nextSerialNo(String typeCode, String scope) {
            AssertUtils.notEmpty(typeCode, "Miss typeCode arguments");

            String day = DateUtils.format(new Date(), DateUtils.YYYYMMDD);
            String sequenceKey = SEQUENCEID_PREFIX + (ObjectUtils.isNotEmpty(scope) ?
                    scope.concat("_").concat(typeCode).concat("_").concat(day) : typeCode.concat("_").concat(day));
            try {
                //TODO: Serious problem here, maybe duplicate servialNo when REDIS down
                // 4 days for expire policy
                Long sequenceId = redisSystemService.incAndGet(sequenceKey, 60 * 60 * 24 * 4);
                String prefix = day.concat(typeCode);
                if (sequenceId < TEN) {
                    return prefix.concat("000").concat(Long.toString(sequenceId));
                } else if (sequenceId < HUNDRED) {
                    return prefix.concat("00").concat(Long.toString(sequenceId));
                } else if (sequenceId < THOUSAND) {
                    return prefix.concat("0").concat(Long.toString(sequenceId));
                } else {
                    return prefix.concat(Long.toString(sequenceId));
                }
            } catch (RedisSystemException cse) {
                throw new RuntimeException("Failed to generate serial key for " + sequenceKey);
            }
        }
    }

    public enum SequenceKey {
        // Sequence key list
        TEST_SEQUENCE("TEST_SEQUENCE"),

        FUND_TRANSACTION("FUND_TRANSACTION");

        private String key;

        SequenceKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return identifier();
        }

        public String identifier() {
            return key;
        }
    }

    private class KeyEntry {
        public SequenceKey key;
        public String scope;

        public KeyEntry(SequenceKey key, String scope) {
            this.key = key;
            this.scope = scope;
        }

        @Override
        public int hashCode() {
            // Key must be not null
            int hashCode = key.hashCode();
            if (scope != null) {
                hashCode = hashCode + scope.hashCode();
            }
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof KeyEntry) {
                KeyEntry cachedKey = (KeyEntry) obj;
                // Key must be not null
                if (key.equals(cachedKey.key)) {
                    return ObjectUtils.equals(scope, cachedKey.scope);
                }
            }

            return false;
        }
    }
}
